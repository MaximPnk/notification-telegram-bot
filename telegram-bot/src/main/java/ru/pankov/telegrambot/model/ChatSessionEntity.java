package ru.pankov.telegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pankov.telegrambot.common.UserSessionStage;

import javax.persistence.*;

@Entity
@Table(name = "chat_session")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatSessionEntity {

    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "user_session_stage_id")
    private UserSessionStage userSessionStage;
}
