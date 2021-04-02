package ru.pankov.telegrambot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.pankov.common.NotificationDTO;
import ru.pankov.common.NotificationParams;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationClient notificationClient;
    private ObjectMapper mapper;

    @Value("${rest.token}")
    String token;

    @PostConstruct
    private void init() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    public List<NotificationDTO> getByChatId(Long chatId) {
        return mapper.convertValue(notificationClient.getByChatId(chatId, token).getBody().getMessage(), new TypeReference<List<NotificationDTO>>() {});
    }

    public void create(NotificationParams params) {
        notificationClient.create(params, token);
    }

    public void deleteById(Long id) {
        notificationClient.deleteById(id, token);
    }

}
