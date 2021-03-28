package ru.pankov.notificationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.pankov.common.Header;
import ru.pankov.common.NotificationParams;
import ru.pankov.notificationservice.dao.NotificationRepository;
import ru.pankov.notificationservice.entity.NotificationEntity;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    @Async
    @Transactional
    public CompletableFuture<Header> save(NotificationParams params) {
        NotificationEntity notificationEntity = notificationRepository.save(new NotificationEntity(params));
        return CompletableFuture.completedFuture(Header.ok(notificationEntity));
    }

    @Override
    @Transactional
    public Header findByChatId(Long chatId) {
        List<NotificationEntity> notifications = notificationRepository.findByChatId(chatId);
        return Header.ok(notifications);
    }

    @Override
    @Async
    @Transactional
    public void deleteById(Long id) {
        try {
            notificationRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ignored) {}
    }
}