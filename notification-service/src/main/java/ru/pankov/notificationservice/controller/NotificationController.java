package ru.pankov.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/{chatId}")
    public ResponseEntity<Header> getByChatId(@PathVariable Long chatId) {
        log.info("Received get request");
        return new ResponseEntity<>(notificationService.findByChatId(chatId), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Header> create(@RequestBody NotificationParams params) {
        log.info("Received create request");
        notificationService.save(params);
        return new ResponseEntity<>(Header.ok(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Header> deleteById(@PathVariable Long id) {
        log.info("Received delete request");
        notificationService.deleteById(id);
        return new ResponseEntity<>(Header.ok(), HttpStatus.OK);
    }
}
