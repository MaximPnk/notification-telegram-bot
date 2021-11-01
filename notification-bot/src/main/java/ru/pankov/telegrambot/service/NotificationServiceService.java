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
public class NotificationServiceService {

    private final NotificationServiceClient notificationServiceClient;
    private ObjectMapper mapper;

    @Value("${rest.token}")
    String token;

    @PostConstruct
    private void init() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    public List<NotificationDTO> getByChatId(Long chatId) {
        return mapper.convertValue(notificationServiceClient.getByChatId(chatId, token).getBody().getMessage(), new TypeReference<List<NotificationDTO>>() {});
    }

    public void create(NotificationParams params) {
        notificationServiceClient.create(params, token);
    }

    public void deleteById(Long id) {
        notificationServiceClient.deleteById(id, token);
    }

}
