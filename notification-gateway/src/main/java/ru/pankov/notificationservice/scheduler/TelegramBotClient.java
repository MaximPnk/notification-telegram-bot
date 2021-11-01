package ru.pankov.notificationservice.scheduler;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pankov.common.Header;
import ru.pankov.common.NotificationDTO;

@FeignClient("notification-bot")
@RequestMapping("/notification-bot/send")
public interface TelegramBotClient {

    @PostMapping("/")
    ResponseEntity<Header> sendMessage(@RequestBody NotificationDTO notificationDTO, @RequestHeader("auth") String token);
}
