package ru.pankov.notificationservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pankov.common.NotificationParams;
import ru.pankov.common.NotificationType;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime date;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "notification_type")
    private NotificationType notificationType;

    @Column(name = "text")
    private String text;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "sent")
    private Boolean sent;

    public NotificationEntity(NotificationParams params) {
        if (params.getType() == NotificationType.BIRTHDAY) {
            params.setDate(params.getDate().withHour(10).withMinute(0).withSecond(0));
        }
        this.chatId = params.getChatId();
        this.date = params.getDate();
        this.notificationType = params.getType();
        this.text = params.getText();
        this.sent = false;
    }

    public static NotificationEntity getPreparedBirthday(NotificationEntity entity) {
        NotificationEntity n = new NotificationEntity();
        n.chatId = entity.getChatId();
        n.date = entity.getDate().minusDays(3);
        n.notificationType = entity.getNotificationType();
        n.text = entity.getText();
        n.parentId = entity.getId();
        n.sent = false;
        return n;
    }
}
