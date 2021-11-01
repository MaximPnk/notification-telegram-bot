package ru.pankov.telegrambot.handler;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.pankov.common.Period;
import ru.pankov.telegrambot.bot.Response;
import ru.pankov.telegrambot.common.MessageType;
import ru.pankov.telegrambot.model.ChatSessionEntity;

import java.time.DateTimeException;
import java.time.LocalDateTime;

@Slf4j
public class AddEventHandler extends Handler {

    public static Response handleName(Message requestMessage, SendMessage responseMessage, ChatSessionEntity chatSessionEntity) {

        Response response = new Response();
        handleCommands(requestMessage, responseMessage, response);
        if (response.getMessage() != null && response.getMessageType() != null) {
            return response;
        }

        chatSessionEntity.setTmpName(requestMessage.getText());
        responseMessage.setText(ResponseTextGenerator.getAddEventDateText());
        response.setMessageType(MessageType.ADD_EVENT_DATE);
        response.setMessage(responseMessage);
        return response;
    }

    public static Response handleDate(Message requestMessage, SendMessage responseMessage, ChatSessionEntity chatSessionEntity) {

        Response response = new Response(responseMessage);
        handleCommands(requestMessage, responseMessage, response);
        if (response.getMessage() != null && response.getMessageType() != null) {
            return response;
        }

        switch (requestMessage.getText()) {
            case "⏪ Пред":
                LocalDateTime prev = chatSessionEntity.getTmpDate().minusMonths(1).withDayOfMonth(1);
                chatSessionEntity.setTmpDate(prev.compareTo(LocalDateTime.now()) < 0 ? LocalDateTime.now() : prev);
                responseMessage.setText("Назад");
                response.setMessageType(MessageType.ADD_EVENT_DATE);
                break;
            case "След ⏩":
                chatSessionEntity.setTmpDate(chatSessionEntity.getTmpDate().plusMonths(1).withDayOfMonth(1));
                responseMessage.setText("Вперёд");
                response.setMessageType(MessageType.ADD_EVENT_DATE);
                break;
            default:
                try {
                    int day = Integer.parseInt(requestMessage.getText());
                    LocalDateTime bd = chatSessionEntity.getTmpDate().withDayOfMonth(day);
                    chatSessionEntity.setTmpDate(bd);
                } catch (NumberFormatException | DateTimeException e) {
                    responseMessage.setText("Неверно указана дата");
                    response.setMessageType(MessageType.ADD_EVENT_DATE);
                    return response;
                }
                responseMessage.setText(ResponseTextGenerator.getAddEventHoursText());
                response.setMessageType(MessageType.ADD_EVENT_HOURS);
        }
        return response;
    }

    public static Response handleHours(Message requestMessage, SendMessage responseMessage, ChatSessionEntity chatSessionEntity) {

        Response response = new Response(responseMessage);
        handleCommands(requestMessage, responseMessage, response);
        if (response.getMessage() != null && response.getMessageType() != null) {
            return response;
        }

        try {
            int hour = Integer.parseInt(requestMessage.getText());
            LocalDateTime bd = chatSessionEntity.getTmpDate().withHour(hour);
            chatSessionEntity.setTmpDate(bd);
        } catch (NumberFormatException | DateTimeException e) {
            responseMessage.setText("Неверно указана дата");
            response.setMessageType(MessageType.ADD_EVENT_HOURS);
            return response;
        }
        responseMessage.setText(ResponseTextGenerator.getAddEventMinutesText());
        response.setMessageType(MessageType.ADD_EVENT_MINUTES);

        return response;
    }

    public static Response handleMinutes(Message requestMessage, SendMessage responseMessage, ChatSessionEntity chatSessionEntity) {

        Response response = new Response(responseMessage);
        handleCommands(requestMessage, responseMessage, response);
        if (response.getMessage() != null && response.getMessageType() != null) {
            return response;
        }

        try {
            int minute = Integer.parseInt(requestMessage.getText().split(":")[1]);
            LocalDateTime bd = chatSessionEntity.getTmpDate().withMinute(minute);
            chatSessionEntity.setTmpDate(bd);
        } catch (Exception e) {
            responseMessage.setText("Неверно указана дата");
            response.setMessageType(MessageType.ADD_EVENT_MINUTES);
            return response;
        }
        responseMessage.setText(ResponseTextGenerator.getAddEventPeriodText());
        response.setMessageType(MessageType.ADD_PERIOD);

        return response;
    }

    public static Response handlePeriod(Message requestMessage, SendMessage responseMessage, ChatSessionEntity entity) {

        Response response = new Response();
        handleCommands(requestMessage, responseMessage, response);
        if (response.getMessage() != null && response.getMessageType() != null) {
            return response;
        }

        switch (requestMessage.getText()) {
            case "Один раз \uD83D\uDD34":
                entity.setPeriod(Period.ONCE);
                responseMessage.setText(ResponseTextGenerator.getCreateText());
                response.setMessageType(MessageType.CREATE_EVENT);
                break;
            case "Каждый день \uD83D\uDFE1":
                entity.setPeriod(Period.DAY);
                responseMessage.setText(ResponseTextGenerator.getCreateText());
                response.setMessageType(MessageType.CREATE_EVENT);
                break;
            case "Каждую неделю \uD83D\uDFE2":
                entity.setPeriod(Period.WEEK);
                responseMessage.setText(ResponseTextGenerator.getCreateText());
                response.setMessageType(MessageType.CREATE_EVENT);
                break;
            case "Каждый месяц \uD83D\uDD35":
                entity.setPeriod(Period.MONTH);
                responseMessage.setText(ResponseTextGenerator.getCreateText());
                response.setMessageType(MessageType.CREATE_EVENT);
                break;
            case "Каждый год \uD83D\uDFE3":
                entity.setPeriod(Period.YEAR);
                responseMessage.setText(ResponseTextGenerator.getCreateText());
                response.setMessageType(MessageType.CREATE_EVENT);
                break;
            default:
                responseMessage.setText(ResponseTextGenerator.getUnsupportedText());
                response.setMessageType(MessageType.UNSUPPORTED);
        }
        response.setMessage(responseMessage);

        return response;
    }

}
