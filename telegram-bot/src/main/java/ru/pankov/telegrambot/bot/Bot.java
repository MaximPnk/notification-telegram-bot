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
import ru.pankov.common.NotificationParams;
import ru.pankov.common.NotificationType;
import ru.pankov.common.Period;
import ru.pankov.telegrambot.common.MessageType;
import ru.pankov.telegrambot.common.UserSessionStage;
import ru.pankov.telegrambot.service.NotificationServiceService;
import ru.pankov.telegrambot.handler.*;
import ru.pankov.telegrambot.model.ChatSessionEntity;
import ru.pankov.telegrambot.service.ChatSessionService;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {

    private final ChatSessionService chatSessionService;
    private final NotificationServiceService notificationServiceService;

    @Value("${bot.name}")
    String botUsername;

    @Value("${bot.token}")
    String botToken;

    Message requestMessage;
    Long chatId;
    ChatSessionEntity chatSession;
    UserSessionStage userSessionStage;
    Response response;
    SendMessage responseMessage;


    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {

            //получаю сообщение
            requestMessage = update.getMessage();
            chatId = requestMessage.getChatId();
            log.info(String.format("Received msg from chat = \"%d\" command = \"%s\"", chatId, requestMessage.getText()));

            //получаю метод юзера
            chatSession = chatSessionService.getChatSessionById(chatId).orElse(chatSessionService.save(new ChatSessionEntity(chatId, UserSessionStage.MAIN_STAGE)));
            userSessionStage = chatSession.getUserSessionStage();

            //создаю прототип ответа
            response = new Response();
            responseMessage = new SendMessage();
            responseMessage.setChatId(String.valueOf(chatId));
            responseMessage.setParseMode("html");

            //заполняю ответ
            handleMessage();

            //перевожу сессию на другую стадию
            changeState();

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

    private void handleMessage() {
        switch (userSessionStage) {
            case MAIN_STAGE:
                response = MainHandler.handle(requestMessage, responseMessage, notificationServiceService.getByChatId(chatId));
                break;
            case ADD_STAGE:
                response = AddHandler.handle(requestMessage, responseMessage);
                break;
            case ADD_BIRTHDAY_NAME_STAGE:
                response = AddBirthdayHandler.handleName(requestMessage, responseMessage, chatSession);
                break;
            case ADD_BIRTHDAY_DATE_STAGE:
                response = AddBirthdayHandler.handleDate(requestMessage, responseMessage, chatSession);
                if (response.getMessageType() != MessageType.ADD_BIRTHDAY_DATE && response.getMessageType() != MessageType.CREATE_BIRTHDAY) {
                    chatSession.setTmpDate(LocalDateTime.now());
                }
                break;
            case ADD_EVENT_NAME_STAGE:
                response = AddEventHandler.handleName(requestMessage, responseMessage, chatSession);
                break;
            case ADD_EVENT_DATE_STAGE:
                response = AddEventHandler.handleDate(requestMessage, responseMessage, chatSession);
                if (response.getMessageType() != MessageType.ADD_EVENT_DATE && response.getMessageType() != MessageType.ADD_EVENT_HOURS) {
                    chatSession.setTmpDate(LocalDateTime.now());
                }
                break;
            case ADD_EVENT_HOURS_STAGE:
                response = AddEventHandler.handleHours(requestMessage, responseMessage, chatSession);
                if (response.getMessageType() != MessageType.ADD_EVENT_HOURS && response.getMessageType() != MessageType.ADD_EVENT_MINUTES) {
                    chatSession.setTmpDate(LocalDateTime.now());
                }
                break;
            case ADD_EVENT_MINUTES_STAGE:
                response = AddEventHandler.handleMinutes(requestMessage, responseMessage, chatSession);
                if (response.getMessageType() != MessageType.ADD_EVENT_MINUTES && response.getMessageType() != MessageType.ADD_PERIOD) {
                    chatSession.setTmpDate(LocalDateTime.now());
                }
                break;
            case ADD_PERIOD_STAGE:
                response = AddEventHandler.handlePeriod(requestMessage, responseMessage, chatSession);
                break;
            case GET_STAGE:
                response = GetHandler.handle(requestMessage, responseMessage, notificationServiceService.getByChatId(chatId));
                break;
            case DELETE_LIST_STAGE:
                response = DeleteHandler.handleList(requestMessage, responseMessage, notificationServiceService.getByChatId(chatId), chatSession);
                break;
            case DELETE_CONFIRM_STAGE:
                response = DeleteHandler.handleConfirm(requestMessage, responseMessage, notificationServiceService.getByChatId(chatId));
                break;
        }
    }

    private void changeState() {
        switch (response.getMessageType()) {
            case DELETE:
                notificationServiceService.deleteById(chatSession.getTmpNotificationId());
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
                if (chatSession.getTmpDate() == null) {
                    chatSession.setTmpDate(LocalDateTime.now());
                }
                KeyboardChanger.setDateButtons(responseMessage, chatSession.getTmpDate());
                chatSession.setUserSessionStage(UserSessionStage.ADD_BIRTHDAY_DATE_STAGE);
                break;
            case CREATE_BIRTHDAY:
                notificationServiceService.create(new NotificationParams(chatId, NotificationType.BIRTHDAY, chatSession.getTmpDate(), Period.YEAR, chatSession.getTmpName()));
                KeyboardChanger.setMainMenuButtons(responseMessage);
                chatSession.setUserSessionStage(UserSessionStage.MAIN_STAGE);
                chatSession.setTmpDate(LocalDateTime.now());
                break;
            case ADD_EVENT_NAME:
                KeyboardChanger.removeButtons(responseMessage);
                chatSession.setUserSessionStage(UserSessionStage.ADD_EVENT_NAME_STAGE);
                break;
            case ADD_EVENT_DATE:
                if (chatSession.getTmpDate() == null) {
                    chatSession.setTmpDate(LocalDateTime.now());
                }
                KeyboardChanger.setDateButtons(responseMessage, chatSession.getTmpDate());
                chatSession.setUserSessionStage(UserSessionStage.ADD_EVENT_DATE_STAGE);
                break;
            case ADD_EVENT_HOURS:
                KeyboardChanger.setHoursButtons(responseMessage, chatSession);
                chatSession.setUserSessionStage(UserSessionStage.ADD_EVENT_HOURS_STAGE);
                break;
            case ADD_EVENT_MINUTES:
                KeyboardChanger.setMinutesButtons(responseMessage, chatSession);
                chatSession.setUserSessionStage(UserSessionStage.ADD_EVENT_MINUTES_STAGE);
                break;
            case ADD_PERIOD:
                KeyboardChanger.setPeriodButtons(responseMessage);
                chatSession.setUserSessionStage(UserSessionStage.ADD_PERIOD_STAGE);
                break;
            case CREATE_EVENT:
                notificationServiceService.create(new NotificationParams(chatId, NotificationType.EVENT, chatSession.getTmpDate(), chatSession.getPeriod(), chatSession.getTmpName()));
                KeyboardChanger.setMainMenuButtons(responseMessage);
                chatSession.setUserSessionStage(UserSessionStage.MAIN_STAGE);
                chatSession.setTmpDate(LocalDateTime.now());
                break;
            case GET:
                KeyboardChanger.setGetButtons(responseMessage);
                chatSession.setUserSessionStage(UserSessionStage.GET_STAGE);
                break;
            case DELETE_LIST:
                KeyboardChanger.setDeleteButtons(responseMessage, notificationServiceService.getByChatId(chatId));
                chatSession.setUserSessionStage(UserSessionStage.DELETE_LIST_STAGE);
                break;
            case DELETE_CONFIRM:
                KeyboardChanger.setDeleteConfirmButtons(responseMessage);
                chatSession.setUserSessionStage(UserSessionStage.DELETE_CONFIRM_STAGE);
                break;

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
