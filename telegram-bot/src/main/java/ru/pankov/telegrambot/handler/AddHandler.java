package ru.pankov.telegrambot.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.pankov.telegrambot.bot.Response;
import ru.pankov.telegrambot.common.MessageType;

@Component
public class AddHandler extends Handler {

    public Response handle(Message requestMessage, SendMessage responseMessage) {

        Response response = new Response();

        switch (requestMessage.getText()) {
            case "День рождения✔️":
                responseMessage.setText(getAddBirthdayNameText());
                response.setMessageType(MessageType.ADD_BIRTHDAY_NAME);
                break;
            case "Другое событие✔️":
                //responseMessage.setText(getAddText());
                //response.setMessageType(MessageType.ADD);
                //break;
            case "/start":
                responseMessage.setText(getStartText(requestMessage.getFrom().getUserName()));
                response.setMessageType(MessageType.START);
                break;
            case "Вернуться✔️":
            case "/return":
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
