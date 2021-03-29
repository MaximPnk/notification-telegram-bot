package ru.pankov.notificationservice.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import ru.pankov.notificationservice.entity.NotificationEntity;

@Getter
@Slf4j
public class AddAdvanceBirthdayNotify extends ApplicationEvent {

    private final NotificationEntity notification;

    public AddAdvanceBirthdayNotify(Object source, NotificationEntity notification) {
        super(source);
        this.notification = notification;
    }
}
