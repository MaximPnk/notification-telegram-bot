package ru.pankov.notificationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.pankov.common.Header;
import ru.pankov.common.NotificationDTO;
import ru.pankov.common.NotificationParams;
import ru.pankov.notificationservice.dao.NotificationRepository;
import ru.pankov.notificationservice.entity.NotificationEntity;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final ApplicationEventPublisher publisher;

    @Override
    @Async
    @Transactional
    public CompletableFuture<Header> save(NotificationParams params) {
        NotificationEntity notificationEntity = notificationRepository.save(new NotificationEntity(params));
        publisher.publishEvent(new AddAdvanceBirthdayNotify(this, notificationEntity));
        return CompletableFuture.completedFuture(Header.ok(notificationEntity));
    }

    @Override
    @Transactional
    public Header findByChatId(Long chatId) {
        List<NotificationEntity> notifications = notificationRepository.findByChatIdAndIsAdvanceFalse(chatId);
        return Header.ok(notifications.stream().map(n -> new NotificationDTO(n.getId(), n.getNotificationType(), n.getDate(), n.getText())));
    }

    @Override
    @Async
    @Transactional
    public void deleteById(Long id) {
        try {
            notificationRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ignored) {}
    }

    @Override
    @Transactional
    @EventListener
    public void saveAdvanceBirthday(AddAdvanceBirthdayNotify event) {
        notificationRepository.save(NotificationEntity.getPreparedBirthday(event.getNotification()));
    }
}
