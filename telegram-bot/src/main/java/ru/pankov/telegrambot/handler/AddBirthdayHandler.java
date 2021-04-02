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
public class AddBirthdayHandler extends Handler {

    public static Response handleName(Message requestMessage, SendMessage responseMessage, ChatSessionEntity chatSessionEntity) {

        Response response = new Response();
        handleCommands(requestMessage, responseMessage, response);
        if (response.getMessage() != null && response.getMessageType() != null) {
            return response;
        }

        chatSessionEntity.setTmpName(requestMessage.getText());
        responseMessage.setText(ResponseTextGenerator.getAddBirthdayDateText(requestMessage.getText()));
        response.setMessageType(MessageType.ADD_BIRTHDAY_DATE);
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
                LocalDateTime prev = chatSessionEntity.getTmpDate().minusMonths(1).withDayOfMonth(1);
                chatSessionEntity.setTmpDate(prev.compareTo(LocalDateTime.now()) < 0 ? LocalDateTime.now() : prev);
                responseMessage.setText("Назад");
                response.setMessageType(MessageType.ADD_BIRTHDAY_DATE);
                break;
            case "След ⏩":
                chatSessionEntity.setTmpDate(chatSessionEntity.getTmpDate().plusMonths(1).withDayOfMonth(1));
                responseMessage.setText("Вперёд");
                response.setMessageType(MessageType.ADD_BIRTHDAY_DATE);
                break;
            default:
                try {
                    int day = Integer.parseInt(requestMessage.getText());
                    LocalDateTime bd = chatSessionEntity.getTmpDate().withDayOfMonth(day);
                    chatSessionEntity.setTmpDate(bd);
                } catch (NumberFormatException | DateTimeException e) {
                    responseMessage.setText("Неверно указана дата");
                    response.setMessageType(MessageType.ADD_BIRTHDAY_DATE);
                    return response;
                }
                responseMessage.setText(ResponseTextGenerator.getCreateText());
                response.setMessageType(MessageType.CREATE_BIRTHDAY);
        }
        return response;
    }

}
