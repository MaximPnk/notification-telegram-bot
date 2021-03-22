package ru.pankov.telegrambot.handler;

public class Handler {

    protected static String getAddText() {
        return "Приступим к созданию нового уведомления." + System.lineSeparator() + System.lineSeparator() +
                "Пожалуйста, выбери из списка интересующую категорию.";
    }

    protected static String getStartText(String userName) {
        return "Привет, " + userName + "!\uD83D\uDC4B\uD83C\uDFFC" + System.lineSeparator() + System.lineSeparator() +
                "Я твой бот, который поможет тебе никогда не забывать о дне рождения, списке дел или предстоящем мероприятии❗️" + System.lineSeparator() + System.lineSeparator() +
                "Я умею не только запоминать твои важные события, но и напоминать о них заранее✅";
    }

    protected static String getHelpText() {
        return "Для управления ботом необходимо использовать кнопки, которые заменяют клавиатуру." + System.lineSeparator() +
                "В случае возникновения вопросов можно обратиться @your_notify_bot";
    }

    protected static String getUnsupportedText() {
        return "Пожалуйста, выберите команду из списка";
    }

    protected static String getReturnText() {
        return "Вернёмся в главное меню";
    }
}
