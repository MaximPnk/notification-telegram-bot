package ru.pankov.telegrambot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pankov.telegrambot.model.ChatSessionEntity;
import ru.pankov.telegrambot.repository.ChatSessionRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatSessionServiceImpl implements ChatSessionService {

    private final ChatSessionRepository chatSessionRepository;

    @Override
    public Optional<ChatSessionEntity> getChatSessionById(Long id) {
        return chatSessionRepository.findById(id);
    }

    @Override
    public ChatSessionEntity save(ChatSessionEntity chatSessionEntity) {
        return chatSessionRepository.save(chatSessionEntity);
    }
}
