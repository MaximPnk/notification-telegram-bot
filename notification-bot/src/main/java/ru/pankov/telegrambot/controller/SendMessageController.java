package ru.pankov.telegrambot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.pankov.common.Header;
import ru.pankov.common.NotificationDTO;
import ru.pankov.telegrambot.bot.Bot;
import ru.pankov.telegrambot.handler.ResponseTextGenerator;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/send")
@Slf4j
@RequiredArgsConstructor
public class SendMessageController {

    private final Bot bot;

    @Value("${rest.token}")
    String token;

    @PostMapping("/")
    @Transactional
    public ResponseEntity<Header> sendMessage(@RequestBody NotificationDTO n, @RequestHeader("auth") String token) {
        if (!this.token.equals(token)) {
            return new ResponseEntity<>(new Header(null, 1, "FORBIDDEN"), HttpStatus.FORBIDDEN);
        }
        SendMessage msg = new SendMessage();
        msg.setChatId(String.valueOf(n.getChatId()));
        msg.setText(ResponseTextGenerator.getSendText(n));
        try {
            bot.execute(msg);
            log.info(String.format("Sent event to chat = \"%d\" event type = \"%s\"", n.getChatId(), n.getType().getValue()));
        } catch (TelegramApiException e) {
            log.info(String.format("Failed to send event to chat = \"%d\" event type = \"%s\"", n.getChatId(), n.getType().getValue()));
            return new ResponseEntity<>(new Header(null, 2, "FAILED TO SEND"), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(Header.ok(), HttpStatus.OK);
    }
}
