package ru.pankov.telegrambot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.pankov.telegrambot.bot.Response;
import ru.pankov.telegrambot.common.MessageType;

public class AddHandler extends Handler {

    public static Response handle(Message requestMessage, SendMessage responseMessage) {

        Response response = new Response();
        handleCommands(requestMessage, responseMessage, response);
        if (response.getMessage() != null && response.getMessageType() != null) {
            return response;
        }

        switch (requestMessage.getText()) {
            case "День рождения \uD83C\uDF89":
                responseMessage.setText(ResponseTextGenerator.getAddBirthdayNameText());
                response.setMessageType(MessageType.ADD_BIRTHDAY_NAME);
                break;
            case "Событие ✅":
                responseMessage.setText(ResponseTextGenerator.getAddEventNameText());
                response.setMessageType(MessageType.ADD_EVENT_NAME);
                break;
            case "Вернуться ↩️":
                responseMessage.setText(ResponseTextGenerator.getReturnText());
                response.setMessageType(MessageType.RETURN);
                break;
            default:
                responseMessage.setText(ResponseTextGenerator.getUnsupportedText());
                response.setMessageType(MessageType.UNSUPPORTED);
        }
        response.setMessage(responseMessage);

        return response;
    }

}
