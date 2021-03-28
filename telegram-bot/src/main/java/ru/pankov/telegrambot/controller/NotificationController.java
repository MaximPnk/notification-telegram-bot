package ru.pankov.telegrambot.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pankov.common.NotificationDTO;
import ru.pankov.common.NotificationParams;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationClient notificationClient;
    private ObjectMapper mapper;

    @PostConstruct
    private void init() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    public List<NotificationDTO> getByChatId(Long chatId) {
        List<NotificationDTO> notifications = mapper.convertValue(notificationClient.getByChatId(chatId).getBody().getMessage(), new TypeReference<>() {});
//        return (List<NotificationDTO>) notificationClient.getByChatId(chatId).getBody().getMessage();
        return notifications;
    }

    public void create(NotificationParams params) {
        notificationClient.create(params);
    }

    public void deleteById(Long id) {
        notificationClient.deleteById(id);
    }

}
