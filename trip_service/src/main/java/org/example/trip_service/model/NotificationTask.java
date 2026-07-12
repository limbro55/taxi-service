package org.example.trip_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Id;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification_tasks")
@Data
public class NotificationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tripId;
    private String message;
    private String status; // PENDING, SENT, FAILED

    private Integer attempts = 0;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "last_attempt")
    private LocalDateTime lastAttempt;

    @Column(name = "processed_by")
    private String processedBy;

}