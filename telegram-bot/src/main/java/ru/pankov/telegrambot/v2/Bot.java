package ru.pankov.telegrambot.v2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
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
            log.info(String.format("Received msg from chat = \"%d\" command = \"%s\"", chatId, message.getText()));


            // проверка на существование сессии
            // присваивание ей необходимого обработчика
            // иначе создание и присваивание ей обработчика start
            // итого у нас есть обработчик, которому мы должны послать сообщение
            // и само сообщение send, которое мы должны обработать в обработчике и вернуть обратно
            // вернуть обратно надо в виде объекта response с полями
            // 1) следующий обработчик
            // 2) тип отсылаемого сообщения
            // 3) само сообщение sendMessage

            Optional<Integer> messageTypeId = repository.getMessageTypeIdByChatId(chatId);
            MessageType messageType;
            if (messageTypeId.isPresent()) {
                messageType = MessageType.values()[messageTypeId.get()];
            } else {
                messageType = repository.save(chatId, MessageType.START.ordinal());
            }

            //ChatSession = telegramSessionService.findSessionById(chatId);

            Response response = new Response();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(chatId));
            sendMessage.setParseMode("html");


            switch (messageType) {
                case START:

            }

            try {
                execute(sendMessage);
                log.info(String.format("Sent msg to chat = \"%d\" command = \"%s\"", chatId, response.getMessageType()));
            } catch (TelegramApiException e) {
                log.error(String.format("Failed to send msg to chat = \"%d\" text = \"%s\"", chatId, response.getMessageType()));
            }

            /*
            ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
            sendMessage.setReplyMarkup(markup);
            markup.setSelective(true);
            markup.setResizeKeyboard(true);
            markup.setOneTimeKeyboard(false);

            List<KeyboardRow> keyboardRows = new ArrayList<>();
            KeyboardRow first = new KeyboardRow();
            first.add("start");
            KeyboardRow second = new KeyboardRow();
            first.add("help");
            keyboardRows.add(first);
            keyboardRows.add(second);
            markup.setKeyboard(keyboardRows);
            */
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
