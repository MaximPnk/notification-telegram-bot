package ru.pankov.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationParams {
    private Long chatId;
    private NotificationType type;
    private LocalDateTime date;
    private Period period;
    private String text;
}
