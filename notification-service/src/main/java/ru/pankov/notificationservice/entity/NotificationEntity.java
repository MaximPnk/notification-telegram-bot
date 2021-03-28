package ru.pankov.notificationservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pankov.common.NotificationParams;
import ru.pankov.common.NotificationType;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Table(name = "notify")
@Entity
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "date")
    private LocalDate date;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "notification_type")
    private NotificationType notificationType;

    @Column(name = "text")
    private String text;

    @Column(name = "sent")
    private Boolean sent;

    public NotificationEntity(NotificationParams params) {
        this.chatId = params.getChatId();
        this.date = params.getDate();
        this.notificationType = params.getType();
        this.text = params.getText();
        this.sent = false;
    }
}
