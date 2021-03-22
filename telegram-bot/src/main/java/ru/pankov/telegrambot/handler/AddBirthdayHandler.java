package ru.pankov.telegrambot.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.pankov.telegrambot.bot.Response;
import ru.pankov.telegrambot.common.MessageType;
import ru.pankov.telegrambot.model.ChatSessionEntity;
import ru.pankov.telegrambot.service.ChatSessionService;

@Component
@RequiredArgsConstructor
public class AddBirthdayHandler extends Handler {

    private final ChatSessionService chatSessionService;

    public Response handleName(Message requestMessage, SendMessage responseMessage) {

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
                ChatSessionEntity chatSessionEntity = chatSessionService.getChatSessionById(requestMessage.getChatId()).get();
                chatSessionEntity.setTmpBDName(requestMessage.getText());
                responseMessage.setText(getAddBirthdayDateText(requestMessage.getText()));
                response.setMessageType(MessageType.ADD_BIRTHDAY_DATE);
        }

        return response;
    }

    public Response handleDate(Message requestMessage, SendMessage responseMessage) {

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
