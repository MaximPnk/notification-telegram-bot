package ru.pankov.telegrambot.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import ru.pankov.common.Period;
import ru.pankov.telegrambot.common.UserSessionStage;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "tmp_name")
    private String tmpName;

    @Column(name = "tmp_date")
    @CreationTimestamp
    private LocalDateTime tmpDate;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tmp_period")
    private Period period;

    @Column(name = "tmp_notification_id")
    private Long tmpNotificationId;

    public ChatSessionEntity(Long chatId, UserSessionStage userSessionStage) {
        this.chatId = chatId;
        this.userSessionStage = userSessionStage;
    }
}
