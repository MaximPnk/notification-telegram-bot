package ru.pankov.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Header {

    private Object message;
    private int code;
    private String description;

    public static Header ok() {
        return new Header(null, 0, "OK");
    }

    public static Header ok(Object message) {
        return new Header(message, 0, "OK");
    }

}
