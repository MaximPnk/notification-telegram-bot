package ru.pankov.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${rest.token}")
    String token;

    @PostMapping("/{chatId}")
    public ResponseEntity<Header> getByChatId(@PathVariable Long chatId, @RequestParam String token) {
        log.info("Received get request");
        if (!this.token.equals(token)) {
            log.info("Get notifications forbidden");
            return new ResponseEntity<>(new Header(null, 1, "FORBIDDEN"), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(notificationService.findByChatId(chatId), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Header> create(@RequestBody NotificationParams params, @RequestParam String token) {
        log.info("Received create request");
        if (!this.token.equals(token)) {
            log.info("Save notification forbidden");
            return new ResponseEntity<>(new Header(null, 1, "FORBIDDEN"), HttpStatus.FORBIDDEN);
        }
        notificationService.save(params);
        return new ResponseEntity<>(Header.ok(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Header> deleteById(@PathVariable Long id, @RequestParam String token) {
        log.info("Received delete request");
        if (!this.token.equals(token)) {
            log.info("Delete notification forbidden");
            return new ResponseEntity<>(new Header(null, 1, "FORBIDDEN"), HttpStatus.FORBIDDEN);
        }
        notificationService.deleteById(id);
        return new ResponseEntity<>(Header.ok(), HttpStatus.OK);
    }
}
