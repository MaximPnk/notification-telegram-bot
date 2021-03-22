package ru.pankov.telegrambot.handler;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.pankov.telegrambot.bot.Response;
import ru.pankov.telegrambot.common.MessageType;
import ru.pankov.telegrambot.model.ChatSessionEntity;

@Slf4j
public class AddBirthdayHandler extends Handler {

    public static Response handleName(Message requestMessage, SendMessage responseMessage, ChatSessionEntity chatSessionEntity) {

        Response response = new Response(responseMessage);

        switch (requestMessage.getText()) {
            case "/start":
                responseMessage.setText(getStartText(requestMessage.getFrom().getUserName()));
                response.setMessageType(MessageType.START);
                break;
            case "Вернуться✔️":
            case "/return":
                responseMessage.setText(getReturnText());
                response.setMessageType(MessageType.RETURN);
                break;
            case "/help":
                responseMessage.setText(getHelpText());
                response.setMessageType(MessageType.HELP);
                break;
            default:
                chatSessionEntity.setTmpBDName(requestMessage.getText());
                responseMessage.setText(getAddBirthdayDateText(requestMessage.getText()));
                response.setMessageType(MessageType.ADD_BIRTHDAY_DATE);
        }

        return response;
    }

    public static Response handleDate(Message requestMessage, SendMessage responseMessage) {

        Response response = new Response(responseMessage);

        switch (requestMessage.getText()) {
            case "/start":
                responseMessage.setText(getStartText(requestMessage.getFrom().getUserName()));
                response.setMessageType(MessageType.START);
                break;
            case "Вернуться✔️":
                responseMessage.setText(getReturnText());
                response.setMessageType(MessageType.RETURN);
                break;
            default:
                responseMessage.setText(getAddBirthdayDateText(requestMessage.getText()));
                response.setMessageType(MessageType.ADD_BIRTHDAY_DATE);
        }

        return response;
    }

}
