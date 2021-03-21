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
import ru.pankov.telegrambot.handler.MainHandler;
import ru.pankov.telegrambot.model.ChatSessionEntity;
import ru.pankov.telegrambot.service.ChatSessionService;

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
            ChatSessionEntity chatSession = chatSessionService.getChatSessionById(chatId).orElse(chatSessionService.save(new ChatSessionEntity(chatId, MessageType.MAIN.ordinal())));
            MessageType messageType = MessageType.values()[chatSession.getMethodId()];

            //создаю прототип ответа
            Response response = new Response();
            SendMessage responseMessage = new SendMessage();
            responseMessage.setChatId(String.valueOf(chatId));
            responseMessage.setParseMode("html");

            //заполняю ответ
            switch (messageType) {
                case MAIN:
                    response = MainHandler.handle(requestMessage, responseMessage);
            }

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

    // проверка на существование сессии
    // присваивание ей необходимого обработчика
    // иначе создание и присваивание ей обработчика start
    // итого у нас есть обработчик, которому мы должны послать сообщение
    // и само сообщение send, которое мы должны обработать в обработчике и вернуть обратно
    // вернуть обратно надо в виде объекта response с полями
    // 1) следующий обработчик
    // 2) тип отсылаемого сообщения
    // 3) само сообщение responseMessage
}
