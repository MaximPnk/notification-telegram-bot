package ru.pankov.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum NotificationType {

    BIRTHDAY("ДР"), EVENT("Событие");

    String value;
}
