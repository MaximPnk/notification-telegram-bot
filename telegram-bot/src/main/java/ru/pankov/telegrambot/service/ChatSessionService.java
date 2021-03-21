package ru.pankov.telegrambot.service;

import ru.pankov.telegrambot.model.ChatSessionEntity;

import java.util.Optional;

public interface ChatSessionService {

    Optional<ChatSessionEntity> getChatSessionById(Long id);

    ChatSessionEntity save(ChatSessionEntity chatSessionEntity);


}
