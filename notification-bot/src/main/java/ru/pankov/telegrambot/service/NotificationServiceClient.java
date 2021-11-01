package ru.pankov.telegrambot.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pankov.common.Header;
import ru.pankov.common.NotificationParams;

@FeignClient("notification-gateway")
@RequestMapping("/notification-gateway/notification")
public interface NotificationServiceClient {

    @GetMapping("/{chatId}")
    ResponseEntity<Header> getByChatId(@PathVariable Long chatId, @RequestHeader("auth") String token);

    @PostMapping("/")
    ResponseEntity<Header> create(@RequestBody NotificationParams params, @RequestHeader("auth") String token);

    @DeleteMapping("/{id}")
    ResponseEntity<Header> deleteById(@PathVariable Long id, @RequestHeader("auth") String token);
}
