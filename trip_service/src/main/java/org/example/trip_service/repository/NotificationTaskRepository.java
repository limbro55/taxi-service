package org.example.trip_service.repository;

import org.example.trip_service.model.NotificationTask; // Проверь путь к модели
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationTaskRepository extends JpaRepository<NotificationTask, Long> {
}