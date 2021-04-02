package ru.pankov.telegrambot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.pankov.common.Header;
import ru.pankov.common.NotificationDTO;

@RestController
@RequestMapping("/send")
@Slf4j
public class SendMessageController {

    @Value("${rest.token}")
    String token;

    @PostMapping("/")
    public Header sendMessage(@RequestBody NotificationDTO notificationDTO, @RequestHeader("auth") String token) {
        log.info(token);
        log.info(this.token);
        if (!this.token.equals(token)) {
            return new Header(null, 1, "FORBIDDEN");
        }
        log.info("it works");
        return Header.ok();
    }
}
