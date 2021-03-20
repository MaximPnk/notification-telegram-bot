package ru.pankov.telegrambot.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Command {
    START("/start"),
    UNSUPPORTED(""), ERROR("");

    private final String value;

    public static Command getCommand(String value) {
        if (START.value.equals(value)) {
            return START;
        } else {
            return UNSUPPORTED;
        }
    }
}
