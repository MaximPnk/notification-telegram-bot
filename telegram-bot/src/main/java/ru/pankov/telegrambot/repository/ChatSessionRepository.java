package ru.pankov.telegrambot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.pankov.telegrambot.model.ChatSessionEntity;

@Repository
public interface ChatSessionRepository extends CrudRepository<ChatSessionEntity, Long> {
}
