package ru.pankov.notificationservice.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.pankov.common.Header;
import ru.pankov.common.NotificationDTO;
import ru.pankov.common.Period;
import ru.pankov.notificationservice.dao.NotificationRepository;
import ru.pankov.notificationservice.entity.NotificationEntity;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class TelegramBotScheduler {

    private final TelegramBotClient client;
    private final NotificationRepository repository;

    @Value("${rest.token}")
    String token;

    @Scheduled(initialDelay = 1000 * 15, fixedDelay = 1000 * 60)
    @Async
    public void sendReadyEvent() {
        log.info("Обработчик событий стартовал");
        List<NotificationEntity> entities = repository.findByDateBefore(LocalDateTime.now());
        log.info(entities.toString());
        entities.forEach(this::sendEach);
    }

    @Transactional
    public void sendEach(NotificationEntity n) {
        log.info("Отправляем");
        Header header = client.sendMessage(new NotificationDTO(n.getId(), n.getChatId(), n.getNotificationType(), n.getDate(), n.getText(), n.getPeriod()), token).getBody();
        log.info(header.toString());
        if (header.getCode() == 0) {
            changeDateByPeriod(n);
            if (n.getPeriod() == Period.ONCE) {
                repository.delete(n);
            } else {
                repository.save(n);
            }
        } else {
            log.error("Сообщение не отправлено");
        }
    }

    private void changeDateByPeriod(NotificationEntity n) {
        switch (n.getNotificationType()) {
            case ADVANCE_BIRTHDAY:
            case BIRTHDAY:
                n.setDate(n.getDate().plusYears(1));
                break;
            case EVENT:
                switch (n.getPeriod()) {
                    case DAY:
                        n.setDate(n.getDate().plusDays(1));
                        break;
                    case WEEK:
                        n.setDate(n.getDate().plusWeeks(1));
                        break;
                    case YEAR:
                        n.setDate(n.getDate().plusYears(1));
                        break;
                    case MONTH:
                        n.setDate(n.getDate().plusMonths(1));
                        break;
                }
                break;
        }
    }
}
