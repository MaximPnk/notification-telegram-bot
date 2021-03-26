package ru.pankov.telegrambot.handler;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.pankov.telegrambot.bot.Response;
import ru.pankov.telegrambot.common.MessageType;
import ru.pankov.telegrambot.model.ChatSessionEntity;

import java.time.DateTimeException;
import java.time.LocalDate;

@Slf4j
public class AddBirthdayHandler extends Handler {

    public static Response handleName(Message requestMessage, SendMessage responseMessage, ChatSessionEntity chatSessionEntity) {

        Response response = new Response();
        handleCommands(requestMessage, responseMessage, response);
        if (response.getMessage() != null && response.getMessageType() != null) {
            return response;
        }

        chatSessionEntity.setTmpBDName(requestMessage.getText());
        responseMessage.setText(getAddBirthdayDateText(requestMessage.getText()));
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
            case "<< Пред":
                LocalDate prev = chatSessionEntity.getTmpBDDate().minusMonths(1).withDayOfMonth(1);
                chatSessionEntity.setTmpBDDate(prev.compareTo(LocalDate.now()) < 0 ? LocalDate.now() : prev);
                responseMessage.setText("Назад");
                response.setMessageType(MessageType.ADD_BIRTHDAY_DATE);
                break;
            case "След >>":
                chatSessionEntity.setTmpBDDate(chatSessionEntity.getTmpBDDate().plusMonths(1).withDayOfMonth(1));
                responseMessage.setText("Вперёд");
                response.setMessageType(MessageType.ADD_BIRTHDAY_DATE);
                break;
            default:
                try {
                    int day = Integer.parseInt(requestMessage.getText());
                    LocalDate bd = chatSessionEntity.getTmpBDDate().withDayOfMonth(day);
                    chatSessionEntity.setTmpBDDate(bd);
                } catch (NumberFormatException | DateTimeException e) {
                    responseMessage.setText("Неверно указана дата");
                    response.setMessageType(MessageType.ADD_BIRTHDAY_DATE);
                    return response;
                }
                responseMessage.setText(getCreateBirthdayText());
                response.setMessageType(MessageType.CREATE_BIRTHDAY);
        }
        return response;
    }

}
