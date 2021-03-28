package ru.pankov.telegrambot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.pankov.common.NotificationDTO;
import ru.pankov.telegrambot.bot.Response;
import ru.pankov.telegrambot.common.MessageType;
import ru.pankov.telegrambot.model.ChatSessionEntity;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class DeleteHandler extends Handler {

    public static Response handleList(Message requestMessage, SendMessage responseMessage, List<NotificationDTO> notifications, ChatSessionEntity chatSession) {

        Response response = new Response();
        response.setMessage(responseMessage);
        handleCommands(requestMessage, responseMessage, response);
        if (response.getMessage() != null && response.getMessageType() != null) {
            return response;
        }

        if (requestMessage.getText().equals("Вернуться✔️")) {
            responseMessage.setText(getReturnText());
            response.setMessageType(MessageType.RETURN);
            return response;
        }

        NotificationDTO n;
        try {
            Long number = Long.parseLong(requestMessage.getText());
            n = notifications.stream().filter(not -> not.getId().equals(number)).findFirst().orElseThrow(IllegalArgumentException::new);
        } catch (IllegalArgumentException e) {
            responseMessage.setText("Неверно указан номер уведомления");
            response.setMessageType(MessageType.DELETE_LIST);
            return response;
        }

        chatSession.setTmpNotificationId(n.getId());
        responseMessage.setText("Вы уверены, что хотите удалить уведомление:" + System.lineSeparator() +
                "#" + n.getId() + ": " + n.getType().getValue() + " - " + n.getText() + " - " + n.getDate().format(DateTimeFormatter.ofPattern("dd MM yyyy")));
        response.setMessageType(MessageType.DELETE_CONFIRM);
        return response;
    }

    public static Response handleConfirm(Message requestMessage, SendMessage responseMessage, List<NotificationDTO> notifications) {

        Response response = new Response();
        handleCommands(requestMessage, responseMessage, response);
        if (response.getMessage() != null && response.getMessageType() != null) {
            return response;
        }

        switch (requestMessage.getText()) {
            case "Да✔️":
                responseMessage.setText(getDeleteSuccessText());
                response.setMessageType(MessageType.DELETE);
                break;
            case "Нет✔️":
                responseMessage.setText(getGetText(notifications));
                response.setMessageType(MessageType.GET);
                break;
            default:
                responseMessage.setText(getUnsupportedText());
                response.setMessageType(MessageType.UNSUPPORTED);
        }
        response.setMessage(responseMessage);

        return response;
    }

}
