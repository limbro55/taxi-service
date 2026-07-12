package org.example.notification_service.worker;

import lombok.RequiredArgsConstructor;
import org.example.notification_service.repository.NotificationRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.concurrent.Executor;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class NotificationScheduler {

    private final NotificationRepository repository;
    private final NotificationWorker worker;
    private final Executor notificationExecutor;

    // Проверяем базу каждую секунду
    @Scheduled(fixedDelay = 1000)
    public void scheduleTasks() {
        repository.findNextTaskForProcessing().ifPresent(task -> {
            // Отдаем задачу в свободный поток из пула
            notificationExecutor.execute(() -> worker.processTask(task));
        });
    }
}