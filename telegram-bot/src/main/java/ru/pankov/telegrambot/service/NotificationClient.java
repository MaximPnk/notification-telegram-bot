package ru.pankov.telegrambot.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pankov.common.Header;
import ru.pankov.common.NotificationParams;

@FeignClient("notification-service")
@RequestMapping("/notification-service/notification")
public interface NotificationClient {

    @PostMapping("/{chatId}")
    ResponseEntity<Header> getByChatId(@PathVariable Long chatId, @RequestParam String token);

    @PostMapping("/")
    ResponseEntity<Header> create(@RequestBody NotificationParams params, @RequestParam String token);

    @DeleteMapping("/{id}")
    ResponseEntity<Header> deleteById(@PathVariable Long id, @RequestParam String token);
}
