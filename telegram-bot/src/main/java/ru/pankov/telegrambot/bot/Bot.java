package ru.pankov.telegrambot.bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.pankov.telegrambot.common.UserSessionStage;
import ru.pankov.telegrambot.handler.AddBirthdayHandler;
import ru.pankov.telegrambot.handler.AddHandler;
import ru.pankov.telegrambot.handler.MainHandler;
import ru.pankov.telegrambot.model.ChatSessionEntity;
import ru.pankov.telegrambot.service.ChatSessionService;

/*
 * TODO
 * Добавляем микросервис с основной БД
 * Микросервисы общаются между собой, либо с помощью eureka
 * Либо загуглить более красивый способ
 * TODO
 * Микросервис с основной БД должен периодически проверять даты
 * В случае успеха добавляет в кафку мсг с инфой по чату и инфе о данном уведомлении
 * В свою очередь бот чекает в потоке инфу от кафки и при получении шлёт сообщение
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {

    private final ChatSessionService chatSessionService;

    @Value("${bot.name}")
    String botUsername;

    @Value("${bot.token}")
    String botToken;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            //получаю сообщение
            Message requestMessage = update.getMessage();
            Long chatId = requestMessage.getChatId();
            log.info(String.format("Received msg from chat = \"%d\" command = \"%s\"", chatId, requestMessage.getText()));

            //получаю метод юзера
            ChatSessionEntity chatSession = chatSessionService.getChatSessionById(chatId).orElse(chatSessionService.save(new ChatSessionEntity(chatId, UserSessionStage.MAIN_STAGE)));
            UserSessionStage userSessionStage = chatSession.getUserSessionStage();

            //создаю прототип ответа
            Response response = new Response();
            SendMessage responseMessage = new SendMessage();
            responseMessage.setChatId(String.valueOf(chatId));
            responseMessage.setParseMode("html");

            //заполняю ответ
            switch (userSessionStage) {
                case MAIN_STAGE:
                    response = MainHandler.handle(requestMessage, responseMessage);
                    break;
                case ADD_STAGE:
                    response = AddHandler.handle(requestMessage, responseMessage);
                    break;
                case ADD_BIRTHDAY_NAME_STAGE:
                    response = AddBirthdayHandler.handleName(requestMessage, responseMessage, chatSession);
                    break;
                case ADD_BIRTHDAY_DATE_STAGE:
                    response = AddBirthdayHandler.handleDate(requestMessage, responseMessage);
                    break;
            }

            //перевожу сессию на другую стадию
            switch (response.getMessageType()) {
                case START:
                case RETURN:
                    KeyboardChanger.setMainMenuButtons(responseMessage);
                    chatSession.setUserSessionStage(UserSessionStage.MAIN_STAGE);
                    break;
                case ADD:
                    KeyboardChanger.setAddMenuButtons(responseMessage);
                    chatSession.setUserSessionStage(UserSessionStage.ADD_STAGE);
                    break;
                case ADD_BIRTHDAY_NAME:
                    KeyboardChanger.removeButtons(responseMessage);
                    chatSession.setUserSessionStage(UserSessionStage.ADD_BIRTHDAY_NAME_STAGE);
                    break;
                case ADD_BIRTHDAY_DATE:
                    KeyboardChanger.setDateButtons(responseMessage, chatSession.getTmpBDDate());
                    chatSession.setUserSessionStage(UserSessionStage.MAIN_STAGE);
                    break;
            }
            chatSessionService.save(chatSession);

            //отправляю
            try {
                execute(response.getMessage());
                log.info(String.format("Sent msg to chat = \"%d\" command = \"%s\"", chatId, response.getMessageType()));
            } catch (TelegramApiException e) {
                log.error(String.format("Failed to send msg to chat = \"%d\" text = \"%s\"", chatId, response.getMessageType()));
            }

        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

}
