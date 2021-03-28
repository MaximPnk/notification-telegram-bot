package ru.pankov.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.pankov.common.Header;
import ru.pankov.common.NotificationParams;
import ru.pankov.notificationservice.service.NotificationService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/{chatId}")
    public ResponseEntity<Header> getByChatId(@PathVariable Long chatId) {
        return new ResponseEntity<>(notificationService.findByChatId(chatId), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Header> create(@RequestBody NotificationParams params) {
        notificationService.save(params);
        return new ResponseEntity<>(Header.ok(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Header> getById(@PathVariable Long id) {
        notificationService.deleteById(id);
        return new ResponseEntity<>(Header.ok(), HttpStatus.OK);
    }
}
