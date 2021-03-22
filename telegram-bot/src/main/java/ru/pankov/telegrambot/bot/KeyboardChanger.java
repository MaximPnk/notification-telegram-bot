package ru.pankov.telegrambot.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyboardChanger {

    public static void setMainMenuButtons(SendMessage responseMessage) {
        ReplyKeyboardMarkup markup = addButtons(responseMessage);
        List<KeyboardRow> keyboardRows = new ArrayList<>() {{
            add(new KeyboardRow() {{ add("Добавить уведомление✔️"); }});
            add(new KeyboardRow() {{ add("Помощь✔️"); }});
        }};
        markup.setKeyboard(keyboardRows);
    }

    public static void setAddMenuButtons(SendMessage responseMessage) {
        ReplyKeyboardMarkup markup = addButtons(responseMessage);
        List<KeyboardRow> keyboardRows = new ArrayList<>() {{
            add(new KeyboardRow() {{ add("День рождения✔️"); add("Другое событие✔️"); }});
            add(new KeyboardRow() {{ add("Вернуться✔️"); }});
        }};
        markup.setKeyboard(keyboardRows);
    }

    private static ReplyKeyboardMarkup addButtons(SendMessage responseMessage) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        responseMessage.setReplyMarkup(markup);
        markup.setSelective(true);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(false);
        return markup;
    }
}
