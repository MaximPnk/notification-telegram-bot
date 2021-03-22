package ru.pankov.telegrambot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.pankov.telegrambot.common.MessageType;
import ru.pankov.telegrambot.bot.Response;

public class MainHandler extends Handler {

    public static Response handle(Message requestMessage, SendMessage responseMessage) {

        Response response = new Response();

        switch (requestMessage.getText()) {
            case "/start":
                responseMessage.setText(getStartText(requestMessage.getFrom().getUserName()));
                response.setMessageType(MessageType.START);
                break;
            case "Добавить уведомление✔️":
                responseMessage.setText(getAddText());
                response.setMessageType(MessageType.ADD);
                break;
            case "Помощь✔️":
                responseMessage.setText(getHelpText());
                response.setMessageType(MessageType.HELP);
                break;
            default:
                responseMessage.setText(getUnsupportedText());
                response.setMessageType(MessageType.UNSUPPORTED);
        }
        response.setMessage(responseMessage);

        return response;
    }

}
