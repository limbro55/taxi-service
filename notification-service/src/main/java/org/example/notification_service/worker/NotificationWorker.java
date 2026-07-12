package org.example.notification_service.worker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.notification_service.entity.NotificationStatus;
import org.example.notification_service.entity.NotificationTask;
import org.example.notification_service.repository.NotificationRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationWorker {

    private final NotificationRepository repository;

    @Transactional
    public void processTask(NotificationTask task) {
        try {
            // 1. Помечаем как "в обработке"
            task.setStatus(NotificationStatus.IN_PROGRESS);
            task.setProcessedBy(Thread.currentThread().getName());
            task.setLastAttempt(LocalDateTime.now());
            repository.saveAndFlush(task);

            // 2. Имитируем отправку (логирование и задержка)
            log.info("Thread {} is sending notification for trip {}: {}",
                    Thread.currentThread().getName(), task.getTripId(), task.getMessage());

            Thread.sleep(2000); // Имитация долгой отправки

            // 3. Успех
            task.setStatus(NotificationStatus.SENT);
        } catch (Exception e) {
            log.error("Failed to send notification {}", task.getId(), e);
            task.setAttempts(task.getAttempts() + 1);
            task.setStatus(NotificationStatus.PENDING); // Возвращаем в очередь для повтора
        } finally {
            repository.save(task);
        }
    }
}