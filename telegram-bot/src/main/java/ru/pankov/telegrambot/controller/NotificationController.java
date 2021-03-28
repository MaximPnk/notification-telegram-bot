package ru.pankov.telegrambot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.pankov.common.Header;
import ru.pankov.common.NotificationDTO;
import ru.pankov.common.NotificationParams;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationClient notificationClient;

    List<NotificationDTO> getByChatId(Long chatId) {
        return (List<NotificationDTO>) notificationClient.getByChatId(chatId).getBody().getMessage();
    }

    void create(NotificationParams params) {
        notificationClient.create(params);
    }

    void deleteById(Long id) {
        notificationClient.deleteById(id);
    }

}
