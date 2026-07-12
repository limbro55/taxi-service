package org.example.notification_service.entity;

public enum NotificationStatus {
    PENDING,      // Ожидает обработки
    IN_PROGRESS,  // Взято воркером
    SENT,         // Успешно отправлено
    FAILED        // Ошибка (после всех попыток)
}