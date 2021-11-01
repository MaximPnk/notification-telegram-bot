package ru.pankov.notificationservice.event;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import ru.pankov.notificationservice.entity.NotificationEntity;

@Getter
@Slf4j
public class DeleteAdvanceBirthday extends ApplicationEvent {

    private final NotificationEntity notification;

    public DeleteAdvanceBirthday(Object source, NotificationEntity notification) {
        super(source);
        this.notification = notification;
    }
}
