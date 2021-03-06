package ru.pankov.telegrambot.bot;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.pankov.common.NotificationDTO;
import ru.pankov.telegrambot.model.ChatSessionEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class KeyboardChanger {

    private final static String[] monthNames = { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };
    private final static String[] hours = { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" };
    private final static String[] minutes = { "00", "15", "30", "45" };


    public static void setMainMenuButtons(SendMessage responseMessage) {
        ReplyKeyboardMarkup markup = addButtons(responseMessage);
        List<KeyboardRow> keyboardRows = new ArrayList<KeyboardRow>() {{
            add(new KeyboardRow() {{ add("Добавить уведомление ☑️"); }});
            add(new KeyboardRow() {{ add("Список моих уведомлений \uD83D\uDCD9"); }});
            add(new KeyboardRow() {{ add("Помощь ⁉️"); }});
        }};
        markup.setKeyboard(keyboardRows);
    }

    public static void setAddMenuButtons(SendMessage responseMessage) {
        ReplyKeyboardMarkup markup = addButtons(responseMessage);
        List<KeyboardRow> keyboardRows = new ArrayList<KeyboardRow>() {{
            add(new KeyboardRow() {{ add("День рождения \uD83C\uDF89"); add("Событие ✅"); }});
            add(new KeyboardRow() {{ add("Вернуться ↩️"); }});
        }};
        markup.setKeyboard(keyboardRows);
    }

    public static void setDateButtons(SendMessage responseMessage, LocalDateTime init) {
        ReplyKeyboardMarkup markup = addButtons(responseMessage);
        LocalDateTime end = init.withDayOfMonth(init.toLocalDate().lengthOfMonth());
        int numberOfWeeks = (int) Math.ceil((double) (init.toLocalDate().lengthOfMonth() - init.getDayOfMonth() + init.getDayOfWeek().getValue()) / 7);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(new KeyboardRow() {{
            add(init.getMonth().getValue() <= LocalDateTime.now().getMonth().getValue() && init.getYear() <= LocalDateTime.now().getYear() ? " " : "⏪ Пред");
            add(monthNames[init.getMonth().getValue() - 1] + " " + init.getYear());
            add("След ⏩");
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

    public static void setHoursButtons(SendMessage responseMessage, ChatSessionEntity entity) {
        ReplyKeyboardMarkup markup = addButtons(responseMessage);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        LocalDateTime eventDate = entity.getTmpDate();
        int hour = 0;
        if (eventDate.toLocalDate().equals(LocalDate.now())) {
            hour = LocalDateTime.now().getHour();
            if (eventDate.getMinute() > 45) {
                hour++;
            }
        }
        for (int i = 0; i < 4; i++) {
            keyboardRows.add(new KeyboardRow());
            for (int j = 0; j < 6 && hour < 24; j++) {
                keyboardRows.get(i).add(hours[hour++]);
            }
        }
        markup.setKeyboard(keyboardRows);
    }

    public static void setMinutesButtons(SendMessage responseMessage, ChatSessionEntity entity) {
        ReplyKeyboardMarkup markup = addButtons(responseMessage);
        LocalDateTime eventDate = entity.getTmpDate();
        List<KeyboardRow> keyboardRows = new ArrayList<KeyboardRow>() {{
            add(new KeyboardRow() {{ add(entity.getTmpDate().getHour() + ":" + minutes[0]); add(entity.getTmpDate().getHour() + ":" + minutes[1]); }});
            add(new KeyboardRow() {{ add(entity.getTmpDate().getHour() + ":" + minutes[2]); add(entity.getTmpDate().getHour() + ":" + minutes[3]); }});
        }};
        if (eventDate.toLocalDate().equals(LocalDate.now()) && eventDate.getHour() == LocalDateTime.now().getHour()) {
            if (LocalDateTime.now().getMinute() > 0) {
                keyboardRows.get(0).remove(0);
            }
            if (LocalDateTime.now().getMinute() > 15) {
                keyboardRows.remove(0);
            }
            if (LocalDateTime.now().getMinute() > 30) {
                keyboardRows.get(0).remove(0);
            }
        }
        markup.setKeyboard(keyboardRows);
    }

    public static void setPeriodButtons(SendMessage responseMessage) {
        ReplyKeyboardMarkup markup = addButtons(responseMessage);
        List<KeyboardRow> keyboardRows = new ArrayList<KeyboardRow>() {{
            add(new KeyboardRow() {{ add("Один раз \uD83D\uDD34"); }});
            add(new KeyboardRow() {{ add("Каждый день \uD83D\uDFE1"); }});
            add(new KeyboardRow() {{ add("Каждую неделю \uD83D\uDFE2"); }});
            add(new KeyboardRow() {{ add("Каждый месяц \uD83D\uDD35"); }});
            add(new KeyboardRow() {{ add("Каждый год \uD83D\uDFE3"); }});
        }};
        markup.setKeyboard(keyboardRows);
    }

    public static void setGetButtons(SendMessage responseMessage) {
        ReplyKeyboardMarkup markup = addButtons(responseMessage);
        List<KeyboardRow> keyboardRows = new ArrayList<KeyboardRow>() {{
            add(new KeyboardRow() {{ add("Удалить по номеру ✖️"); }});
            add(new KeyboardRow() {{ add("Вернуться ↩️"); }});
        }};
        markup.setKeyboard(keyboardRows);
    }

    public static void setDeleteButtons(SendMessage responseMessage, List<NotificationDTO> notifications) {
        ReplyKeyboardMarkup markup = addButtons(responseMessage);
        int numberOfRows = notifications.size() % 4 == 0 ? notifications.size() / 4 : notifications.size() / 4 + 1;
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        for (int i = 0, k = 0; i < numberOfRows; i++) {
            keyboardRows.add(new KeyboardRow());
            for (int j = 0; j < 4 && k < notifications.size(); j++) {
                keyboardRows.get(i).add(String.valueOf(notifications.get(k++).getId()));
            }
        }
        keyboardRows.add(new KeyboardRow() {{ add("Вернуться ↩️"); }});
        markup.setKeyboard(keyboardRows);
    }

    public static void setDeleteConfirmButtons(SendMessage responseMessage) {
        ReplyKeyboardMarkup markup = addButtons(responseMessage);
        List<KeyboardRow> keyboardRows = new ArrayList<KeyboardRow>() {{ add(new KeyboardRow() {{ add("Да ✅"); add("Нет ❌"); }}); }};
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
