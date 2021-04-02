package ru.pankov.notificationservice.service;

import ru.pankov.common.Header;
import ru.pankov.common.NotificationParams;
import ru.pankov.notificationservice.event.AddAdvanceBirthday;
import ru.pankov.notificationservice.event.DeleteAdvanceBirthday;

import java.util.concurrent.CompletableFuture;

public interface NotificationService {

    CompletableFuture<Header> save(NotificationParams params);

    Header findByChatId(Long chatId);

    void deleteById(Long id);

    void saveAdvanceBirthday(AddAdvanceBirthday event);

    void deleteAdvanceBirthday(DeleteAdvanceBirthday event);
}
