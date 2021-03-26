package ru.pankov.telegrambot.bot;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class KeyboardChanger {

    private final static String[] monthNames = { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };

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

    public static void setDateButtons(SendMessage responseMessage, LocalDate init) {
        ReplyKeyboardMarkup markup = addButtons(responseMessage);
        LocalDate end = init.withDayOfMonth(init.lengthOfMonth());
        int numberOfWeeks = (int) Math.ceil((double) (init.lengthOfMonth() - init.getDayOfMonth() + init.getDayOfWeek().getValue()) / 7);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(new KeyboardRow() {{
            add(LocalDate.now().getMonth().getValue() <= init.getMonth().getValue() ? " " : "<< Пред");
            add(monthNames[init.getMonth().getValue()] + " " + init.getYear());
            add("След >>");
        }});
//        keyboardRows.add(new KeyboardRow() {{ add("Пн"); add("Вт"); add("Ср"); add("Чт"); add("Пт"); add("Сб"); add("Вс"); }});
//        в 4 местах логика i+1 вместо i
        for (int i = 1, k = init.getDayOfMonth(); i < numberOfWeeks + 1; i++) {
            keyboardRows.add(new KeyboardRow());
            for (int j = 0; j < 7; j++) {
                if ((i == 1 && j + 1 < init.getDayOfWeek().getValue()) || // меньше первого дня
                        (i == numberOfWeeks && j + 1 > end.getDayOfWeek().getValue())) { // больше последнего дня
                    keyboardRows.get(i).add(" ");
                } else {
                    keyboardRows.get(i).add(String.valueOf(k++));
                }
            }
        }
        markup.setKeyboard(keyboardRows);
    }

    public static void removeButtons(SendMessage responseMessage) {
        ReplyKeyboardRemove remove = new ReplyKeyboardRemove();
        remove.setRemoveKeyboard(true);
        responseMessage.setReplyMarkup(remove);
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
