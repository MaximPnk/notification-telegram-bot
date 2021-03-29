package ru.pankov.notificationservice.service;

import ru.pankov.common.Header;
import ru.pankov.common.NotificationParams;

import java.util.concurrent.CompletableFuture;

public interface NotificationService {

    CompletableFuture<Header> save(NotificationParams params);

    Header findByChatId(Long chatId);

    void deleteById(Long id);

    void saveAdvanceBirthday(AddAdvanceBirthdayNotify event);
}
