package org.example.notification_service.repository;

import org.example.notification_service.entity.NotificationTask;
import org.example.notification_service.entity.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<NotificationTask, Long> {

    @Query(value = """
        SELECT * FROM notification_tasks 
        WHERE status = 'PENDING' AND attempts < 3 
        LIMIT 1 
        FOR UPDATE SKIP LOCKED
        """, nativeQuery = true)
    Optional<NotificationTask> findNextTaskForProcessing();

    List<NotificationTask> findAllByTripId(Long tripId);
}