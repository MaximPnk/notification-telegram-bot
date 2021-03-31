package ru.pankov.telegrambot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.pankov.telegrambot.bot.Response;
import ru.pankov.telegrambot.common.MessageType;

public class Handler {

    protected static void handleCommands(Message requestMessage, SendMessage responseMessage, Response response) {
        switch (requestMessage.getText()) {
            case "/help":
                responseMessage.setText(ResponseTextGenerator.getHelpText());
                response.setMessageType(MessageType.HELP);
                response.setMessage(responseMessage);
                break;
            case "/start":
                responseMessage.setText(ResponseTextGenerator.getStartText(requestMessage.getFrom().getUserName()));
                response.setMessageType(MessageType.START);
                response.setMessage(responseMessage);
                break;
            case "/return":
                responseMessage.setText(ResponseTextGenerator.getReturnText());
                response.setMessageType(MessageType.RETURN);
                response.setMessage(responseMessage);
                break;
        }
    }

}
