package ru.pankov.telegrambot.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

@Component
@Slf4j
public class ServiceCommand extends BotCommand {

    public Response generateAnswer(Message msg) {
        switch (Command.getCommand(msg.getText())) {
            case START:
                return new Response(Command.START, getStartText(msg.getFrom().getUserName()));
            case UNSUPPORTED:
                return new Response(Command.UNSUPPORTED, getUnsupportedText());
            default:
                log.error(getErrorText());
                return new Response(Command.ERROR, getErrorText());
        }
    }

    private String getStartText(String userName) {
        return "Привет, " + userName + "!\uD83D\uDC4B\uD83C\uDFFC" + System.lineSeparator() + System.lineSeparator() +
                "Я твой бот, который поможет тебе никогда не забывать о дне рождения, списке дел или предстоящем мероприятии❗️" + System.lineSeparator() + System.lineSeparator() +
                "Я умею не только запоминать твои важные события, но и напоминать о них заранее✅";
    }

    private String getUnsupportedText() {
        return "Я могу ответить только на команды из списка. Он доступен с помощью команды /help";
    }

    private String getErrorText() {
        return "На стороне сервера произошла ошибка при получении команды";
    }
}
