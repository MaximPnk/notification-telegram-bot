package ru.pankov.telegrambot.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class Bot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    String botUsername;

    @Value("${bot.token}")
    String botToken;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            Long chatId = message.getChatId();
            String text = message.getText();

            SendMessage response = new SendMessage();
            response.setChatId(String.valueOf(chatId));
            String responseText = "echo: " + text;
            response.setText(responseText);

            try {
                execute(response);
                log.info(String.format("Sent msg to chat = \"%d\" text = \"%s\"", chatId, responseText));
            } catch (TelegramApiException e) {
                log.error(String.format("Failed to send msg to chat = \"%d\" text = \"%s\"", chatId, responseText));
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
