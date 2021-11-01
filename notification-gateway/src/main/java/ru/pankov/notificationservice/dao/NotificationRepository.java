package ru.pankov.notificationservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pankov.notificationservice.entity.NotificationEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    List<NotificationEntity> findByChatIdAndParentIdIsNull(Long chatId);

    List<NotificationEntity> findByDateBefore(LocalDateTime localDateTime);

    Optional<NotificationEntity> findByParentId(Long id);
}
