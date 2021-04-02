package ru.pankov.notificationservice.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pankov.common.NotificationDTO;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class NotificationScheduler {

    private final TelegramBotClient telegramBotClient;

    @Value("${rest.encoded.token}")
    String encodedToken;

    @GetMapping("/")
    public void sendReadyEvent() {
        telegramBotClient.sendMessage(new NotificationDTO(), encodedToken);
    }
}
