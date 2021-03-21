package ru.pankov.telegrambot.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.pankov.telegrambot.bot.MessageType;
import ru.pankov.telegrambot.bot.Response;

import java.util.ArrayList;
import java.util.List;

public class MainHandler {

    public static Response handle(Message requestMessage, SendMessage responseMessage) {

        Response response = new Response();

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        responseMessage.setReplyMarkup(markup);
        markup.setSelective(true);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow first = new KeyboardRow();
        first.add("Помощь✔️");
        keyboardRows.add(first);
        markup.setKeyboard(keyboardRows);

        if (requestMessage.getText().equals("/start")) {
            responseMessage.setText(getStartText(requestMessage.getFrom().getUserName()));
            response.setMessageType(MessageType.START);
        } else if (requestMessage.getText().equals("Помощь✔️")) {
            responseMessage.setText(getHelpText());
            response.setMessageType(MessageType.HELP);
        } else {
            responseMessage.setText(getUnsupportedText());
            response.setMessageType(MessageType.UNSUPPORTED);
        }
        response.setMessage(responseMessage);

        return response;
    }

    private static String getStartText(String userName) {
        return "Привет, " + userName + "!\uD83D\uDC4B\uD83C\uDFFC" + System.lineSeparator() + System.lineSeparator() +
                "Я твой бот, который поможет тебе никогда не забывать о дне рождения, списке дел или предстоящем мероприятии❗️" + System.lineSeparator() + System.lineSeparator() +
                "Я умею не только запоминать твои важные события, но и напоминать о них заранее✅";
    }

    private static String getHelpText() {
        return "Для управления ботом необходимо использовать кнопки, которые заменяют клавиатуру." + System.lineSeparator() +
                "В случае возникновения вопросов можно обратиться @your_notify_bot";
    }

    private static String getUnsupportedText() {
        return "Я могу ответить только на команды из списка. Он доступен с помощью команды /help";
    }

}
