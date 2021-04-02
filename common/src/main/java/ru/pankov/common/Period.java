package ru.pankov.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum Period {
    ONCE("Один раз"), DAY("Ежедневно"), WEEK("Еженедельно"), MONTH("Ежемесячно"), YEAR("Ежегодно");

    String value;
}
