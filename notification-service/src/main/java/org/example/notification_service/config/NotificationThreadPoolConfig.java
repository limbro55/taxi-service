package org.example.notification_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class NotificationThreadPoolConfig {

    @Bean(name = "notificationExecutor")
    public Executor notificationExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3); // Начальное количество потоков
        executor.setMaxPoolSize(5);  // Максимум 5 потоков по заданию
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("NotificationWorker-");

        // Graceful Shutdown: ждать завершения задач при остановке
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(30);

        executor.initialize();
        return executor;
    }
}