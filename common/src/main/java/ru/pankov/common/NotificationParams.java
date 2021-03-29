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
    private LocalDateTime date;
    private NotificationType type;
    private String text;
}
