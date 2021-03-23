package ru.pankov.telegrambot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.pankov.telegrambot.bot.Response;
import ru.pankov.telegrambot.common.MessageType;

public class MainHandler extends Handler {

    public static Response handle(Message requestMessage, SendMessage responseMessage) {

        Response response = new Response();
        handleCommands(requestMessage, responseMessage, response);
        if (response.getMessage() != null && response.getMessageType() != null) {
            return response;
        }

        switch (requestMessage.getText()) {
            case "Добавить уведомление✔️":
                responseMessage.setText(getAddText());
                response.setMessageType(MessageType.ADD);
                break;
            case "Помощь✔️":
                responseMessage.setText(getHelpText());
                response.setMessageType(MessageType.HELP);
                break;
            case "Вернуться✔️":
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
