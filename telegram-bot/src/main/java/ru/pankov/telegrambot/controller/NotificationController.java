package ru.pankov.telegrambot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pankov.common.NotificationDTO;
import ru.pankov.common.NotificationParams;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationClient notificationClient;

    public List<NotificationDTO> getByChatId(Long chatId) {
        return (List<NotificationDTO>) notificationClient.getByChatId(chatId).getBody().getMessage();
    }

    public void create(NotificationParams params) {
        notificationClient.create(params);
    }

    public void deleteById(Long id) {
        notificationClient.deleteById(id);
    }

}
