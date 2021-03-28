package ru.pankov.telegrambot.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pankov.common.Header;
import ru.pankov.common.NotificationParams;

@FeignClient("notification-service")
@RequestMapping("/notification")
public interface NotificationClient {

    @GetMapping("/{chatId}")
    ResponseEntity<Header> getByChatId(@PathVariable Long chatId);

    @PostMapping("/")
    ResponseEntity<Header> create(@RequestBody NotificationParams params);

    @DeleteMapping("/{id}")
    ResponseEntity<Header> deleteById(@PathVariable Long id);
}
