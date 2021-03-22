package ru.pankov.telegrambot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.pankov.telegrambot.common.MessageType;
import ru.pankov.telegrambot.bot.Response;

public class AddHandler extends Handler {

    public static Response handle(Message requestMessage, SendMessage responseMessage) {

        Response response = new Response();

        switch (requestMessage.getText()) {
            case "День рождения✔️":
                //responseMessage.setText(getStartText(requestMessage.getFrom().getUserName()));
                //response.setMessageType(MessageType.START);
                //break;
            case "Другое событие✔️":
                //responseMessage.setText(getAddText());
                //response.setMessageType(MessageType.ADD);
                //break;
            default:
                responseMessage.setText(getUnsupportedText());
                response.setMessageType(MessageType.UNSUPPORTED);
                break;
            case "Вернуться✔️":
                responseMessage.setText(getReturnText());
                response.setMessageType(MessageType.RETURN);
                break;
        }
        response.setMessage(responseMessage);

        return response;
    }

}
