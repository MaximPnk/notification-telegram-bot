package ru.pankov.telegrambot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.pankov.telegrambot.bot.Response;
import ru.pankov.telegrambot.common.MessageType;

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
                "Пожалуйста, выбери из списка интересующую категорию.";
    }

    protected static String getAddBirthdayNameText() {
        return "Введи имя человека, день рождения которого ты хотел бы добавить";
    }

    protected static String getAddBirthdayDateText(String name) {
        return "Отлично, давай выберем дату дня рождения для " + name;
    }

    protected static String getCreateBirthdayText() {
        return "Уведомление успешно добавлено";
    }

    protected static String getStartText(String userName) {
        return "Привет, " + userName + "!\uD83D\uDC4B\uD83C\uDFFC" + System.lineSeparator() + System.lineSeparator() +
                "Я твой бот, который поможет тебе никогда не забывать о дне рождения, списке дел или предстоящем мероприятии❗️" + System.lineSeparator() + System.lineSeparator() +
                "Я умею не только запоминать твои важные события, но и напоминать о них заранее✅";
    }

    protected static String getHelpText() {
        return "Для управления ботом необходимо использовать кнопки, которые заменяют клавиатуру." + System.lineSeparator() +
                "Если произошла ошибка, вы всегда можете воспользоваться командой /return для возврата в главное меню" + System.lineSeparator() +
                "В случае возникновения вопросов можно обратиться @your_notify_bot";
    }

    protected static String getUnsupportedText() {
        return "Пожалуйста, выберите команду из списка" + System.lineSeparator() +
                "/help - для получения справочной информации";
    }

    protected static String getReturnText() {
        return "Возврат в главное меню";
    }
}
