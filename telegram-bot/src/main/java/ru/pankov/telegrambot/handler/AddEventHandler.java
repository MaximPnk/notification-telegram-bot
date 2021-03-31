package ru.pankov.telegrambot.handler;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.pankov.telegrambot.bot.Response;
import ru.pankov.telegrambot.common.MessageType;
import ru.pankov.telegrambot.model.ChatSessionEntity;

import java.time.DateTimeException;
import java.time.LocalDateTime;

@Slf4j
public class AddEventHandler extends Handler {

    public static Response handleName(Message requestMessage, SendMessage responseMessage, ChatSessionEntity chatSessionEntity) {

        Response response = new Response();
        handleCommands(requestMessage, responseMessage, response);
        if (response.getMessage() != null && response.getMessageType() != null) {
            return response;
        }

        chatSessionEntity.setTmpBDName(requestMessage.getText());
        responseMessage.setText(getAddEventDateText(requestMessage.getText()));
        response.setMessageType(MessageType.ADD_EVENT_DATE);
        response.setMessage(responseMessage);
        return response;
    }

    public static Response handleDate(Message requestMessage, SendMessage responseMessage, ChatSessionEntity chatSessionEntity) {

        Response response = new Response(responseMessage);
        handleCommands(requestMessage, responseMessage, response);
        if (response.getMessage() != null && response.getMessageType() != null) {
            return response;
        }

        switch (requestMessage.getText()) {
            case "⏪ Пред":
                LocalDateTime prev = chatSessionEntity.getTmpBDDate().minusMonths(1).withDayOfMonth(1);
                chatSessionEntity.setTmpBDDate(prev.compareTo(LocalDateTime.now()) < 0 ? LocalDateTime.now() : prev);
                responseMessage.setText("Назад");
                response.setMessageType(MessageType.ADD_EVENT_DATE);
                break;
            case "След ⏩":
                chatSessionEntity.setTmpBDDate(chatSessionEntity.getTmpBDDate().plusMonths(1).withDayOfMonth(1));
                responseMessage.setText("Вперёд");
                response.setMessageType(MessageType.ADD_EVENT_DATE);
                break;
            default:
                try {
                    int day = Integer.parseInt(requestMessage.getText());
                    LocalDateTime bd = chatSessionEntity.getTmpBDDate().withDayOfMonth(day);
                    chatSessionEntity.setTmpBDDate(bd);
                } catch (NumberFormatException | DateTimeException e) {
                    responseMessage.setText("Неверно указана дата");
                    response.setMessageType(MessageType.ADD_EVENT_DATE);
                    return response;
                }
                responseMessage.setText(getAddEventHoursText());
                response.setMessageType(MessageType.ADD_EVENT_HOURS);
        }
        return response;
    }

    public static Response handleHours(Message requestMessage, SendMessage responseMessage, ChatSessionEntity chatSessionEntity) {

        Response response = new Response(responseMessage);
        handleCommands(requestMessage, responseMessage, response);
        if (response.getMessage() != null && response.getMessageType() != null) {
            return response;
        }

        try {
            int hour = Integer.parseInt(requestMessage.getText());
            LocalDateTime bd = chatSessionEntity.getTmpBDDate().withHour(hour);
            chatSessionEntity.setTmpBDDate(bd);
        } catch (NumberFormatException | DateTimeException e) {
            responseMessage.setText("Неверно указана дата");
            response.setMessageType(MessageType.ADD_EVENT_HOURS);
            return response;
        }
        responseMessage.setText(getAddEventMinutesText());
        response.setMessageType(MessageType.ADD_EVENT_MINUTES);

        return response;
    }

    public static Response handleMinutes(Message requestMessage, SendMessage responseMessage, ChatSessionEntity chatSessionEntity) {

        Response response = new Response(responseMessage);
        handleCommands(requestMessage, responseMessage, response);
        if (response.getMessage() != null && response.getMessageType() != null) {
            return response;
        }

        try {
            int minute = Integer.parseInt(requestMessage.getText().split(":")[1]);
            LocalDateTime bd = chatSessionEntity.getTmpBDDate().withMinute(minute);
            chatSessionEntity.setTmpBDDate(bd);
        } catch (Exception e) {
            responseMessage.setText("Неверно указана дата");
            response.setMessageType(MessageType.ADD_EVENT_MINUTES);
            return response;
        }
        responseMessage.setText(getCreateText());
        response.setMessageType(MessageType.CREATE_EVENT);

        return response;
    }

}
