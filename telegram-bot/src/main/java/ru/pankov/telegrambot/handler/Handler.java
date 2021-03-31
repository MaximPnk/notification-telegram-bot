package ru.pankov.telegrambot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.pankov.common.NotificationDTO;
import ru.pankov.common.NotificationType;
import ru.pankov.telegrambot.bot.Response;
import ru.pankov.telegrambot.common.MessageType;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Handler {

    protected static void handleCommands(Message requestMessage, SendMessage responseMessage, Response response) {
        switch (requestMessage.getText()) {
            case "/help":
                responseMessage.setText(getHelpText());
                response.setMessageType(MessageType.HELP);
                response.setMessage(responseMessage);
                break;
            case "/start":
                responseMessage.setText(getStartText(requestMessage.getFrom().getUserName()));
                response.setMessageType(MessageType.START);
                response.setMessage(responseMessage);
                break;
            case "/return":
                responseMessage.setText(getReturnText());
                response.setMessageType(MessageType.RETURN);
                response.setMessage(responseMessage);
                break;
        }
    }

    protected static String getAddText() {
        return "Приступим к созданию нового уведомления." + System.lineSeparator() + System.lineSeparator() +
                "Выбери из списка интересующую категорию.";
    }

    protected static String getAddBirthdayNameText() {
        return "Введи имя человека, день рождения которого ты хотел бы добавить";
    }

    protected static String getAddBirthdayDateText(String name) {
        return "Отлично, давай выберем дату дня рождения для " + name;
    }

    protected static String getAddEventNameText() {
        return "Введи название события";
    }

    protected static String getAddEventDateText(String name) {
        return "Отлично, давай выберем дату для события: " + name;
    }

    protected static String getAddEventHoursText() {
        return "Выбери час уведомления";
    }

    protected static String getAddEventMinutesText() {
        return "Выбери время уведомления";
    }

    protected static String getDeleteText(List<NotificationDTO> notifications) {
        if (!notifications.isEmpty()) {
            return ("Дни рождения \uD83E\uDD73" + System.lineSeparator() +
                    notifications.stream().filter(n -> n.getType() == NotificationType.BIRTHDAY).sorted(Comparator.comparing(NotificationDTO::getDate))
                            .map(n ->   "(#" + n.getId() + ") " +
                                        n.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " - " +
                                        n.getText())
                            .collect(Collectors.joining(System.lineSeparator())) + System.lineSeparator() + System.lineSeparator() +
                    "События ✅" + System.lineSeparator() +
                    notifications.stream().filter(n -> n.getType() == NotificationType.EVENT).sorted(Comparator.comparing(NotificationDTO::getDate))
                            .map(n ->   "(#" + n.getId() + ") " +
                                        n.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) + " - " +
                                        n.getText())
                            .collect(Collectors.joining(System.lineSeparator())));
        } else {
            return "Список уведомлений пуст";
        }
    }

    protected static String getGetText(List<NotificationDTO> notifications) {
        if (!notifications.isEmpty()) {
            return ("Дни рождения \uD83E\uDD73" + System.lineSeparator() +
                    notifications.stream().filter(n -> n.getType() == NotificationType.BIRTHDAY).sorted(Comparator.comparing(NotificationDTO::getDate))
                            .map(n ->   n.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " - " +
                                        n.getText())
                            .collect(Collectors.joining(System.lineSeparator())) + System.lineSeparator() + System.lineSeparator() +
                    "События ✅" + System.lineSeparator() +
                    notifications.stream().filter(n -> n.getType() == NotificationType.EVENT).sorted(Comparator.comparing(NotificationDTO::getDate))
                            .map(n ->   n.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) + " - " +
                                        n.getText())
                            .collect(Collectors.joining(System.lineSeparator())));
        } else {
            return "Список уведомлений пуст";
        }
    }

    protected static String getDeleteSuccessText() {
        return "Уведомление успешно удалено";
    }

    protected static String getCreateText() {
        return "Уведомление успешно добавлено";
    }

    protected static String getStartText(String userName) {
        return "Привет, " + userName + "!\uD83D\uDC4B\uD83C\uDFFC" + System.lineSeparator() + System.lineSeparator() +
                "Я твой бот, который поможет тебе никогда не забывать о дне рождения, списке дел или предстоящем мероприятии❗️" + System.lineSeparator() + System.lineSeparator() +
                "Я умею не только запоминать твои важные события, но и напоминать о них заранее✅";
    }

    protected static String getHelpText() {
        return "Для управления ботом необходимо использовать кнопки, которые заменяют клавиатуру." + System.lineSeparator() + System.lineSeparator() +
                "/return - возврат в главное меню" + System.lineSeparator() + System.lineSeparator() +
                "Вопросы: @your_notify_bot";
    }

    protected static String getUnsupportedText() {
        return "Пожалуйста, выбери команду из списка" + System.lineSeparator() + System.lineSeparator() +
                "/help - справочная информация";
    }

    protected static String getReturnText() {
        return "Возврат в главное меню";
    }
}
