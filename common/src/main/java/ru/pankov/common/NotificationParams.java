package ru.pankov.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationParams {
    private Long chatId;
    private LocalDate date;
    private NotificationType type;
    private String text;
}
