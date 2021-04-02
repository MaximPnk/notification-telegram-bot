package ru.pankov.notificationservice.scheduler;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.pankov.common.NotificationDTO;

@FeignClient("telegram-bot")
@RequestMapping("/telegram-bot/send")
public interface TelegramBotClient {

    @PostMapping("/")
    void sendMessage(@RequestBody NotificationDTO notificationDTO, @RequestHeader("auth") String token);
}
