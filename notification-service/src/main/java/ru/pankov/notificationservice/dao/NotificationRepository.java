package ru.pankov.notificationservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pankov.notificationservice.entity.NotificationEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    List<NotificationEntity> findByChatIdAndParentIdIsNull(Long chatId);

    Optional<NotificationEntity> findByParentId(Long id);
}
