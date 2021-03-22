package ru.pankov.telegrambot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.pankov.telegrambot.common.MessageType;
import ru.pankov.telegrambot.common.Response;

import java.util.ArrayList;
import java.util.List;

public class MainHandler extends Handler {

    public static Response handle(Message requestMessage, SendMessage responseMessage) {

        Response response = new Response();

        List<KeyboardRow> keyboardRows = SetKeyboardRows.setMainMenuRows();

        switch (requestMessage.getText()) {
            case "/start":
                responseMessage.setText(getStartText(requestMessage.getFrom().getUserName()));
                response.setMessageType(MessageType.START);
                break;
            case "Добавить уведомление✔️":
                keyboardRows = SetKeyboardRows.setAddMenuRows();
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
        response.setKeyboardRows(keyboardRows);

        return response;
    }

}
