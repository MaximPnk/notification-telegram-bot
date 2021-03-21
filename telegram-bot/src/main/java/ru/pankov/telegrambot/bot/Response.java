package ru.pankov.telegrambot.v2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Response {

    private MessageType messageType;
    private SendMessage message;
}
