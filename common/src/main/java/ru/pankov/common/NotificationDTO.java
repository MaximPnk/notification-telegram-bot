package ru.pankov.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    Long id;
    NotificationType type;
    LocalDateTime date;
    String text;
    Period period;
}
