package ru.pankov.telegrambot.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import ru.pankov.telegrambot.common.UserSessionStage;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "chat_session")
@Data
@NoArgsConstructor
public class ChatSessionEntity {

    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "user_session_stage_id")
    private UserSessionStage userSessionStage;

    @Column(name = "tmp_bd_name")
    private String tmpBDName;

    @Column(name = "tmp_bd_date")
    @CreationTimestamp
    private LocalDate tmpBDDate;

    @Column(name = "tmp_notification_id")
    private Long tmpNotificationId;

    public ChatSessionEntity(Long chatId, UserSessionStage userSessionStage) {
        this.chatId = chatId;
        this.userSessionStage = userSessionStage;
    }
}
