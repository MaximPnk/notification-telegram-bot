package ru.pankov.telegrambot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.pankov.common.NotificationDTO;
import ru.pankov.telegrambot.bot.Response;
import ru.pankov.telegrambot.common.MessageType;

import java.util.List;

public class GetHandler extends Handler {

    public static Response handle(Message requestMessage, SendMessage responseMessage, List<NotificationDTO> notifications) {

        Response response = new Response();
        handleCommands(requestMessage, responseMessage, response);
        if (response.getMessage() != null && response.getMessageType() != null) {
            return response;
        }

        switch (requestMessage.getText()) {
            case "Удалить по номеру ✖️":
                responseMessage.setText(getDeleteText(notifications));
                response.setMessageType(MessageType.DELETE_LIST);
                break;
            case "Вернуться ↩️":
                responseMessage.setText(getReturnText());
                response.setMessageType(MessageType.RETURN);
                break;
            default:
                responseMessage.setText(getUnsupportedText());
                response.setMessageType(MessageType.UNSUPPORTED);
        }
        response.setMessage(responseMessage);

        return response;
    }

}
