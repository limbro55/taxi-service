package org.example.notification_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification_tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tripId;

    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    private Integer attempts = 0;

    private LocalDateTime createdAt;

    private LocalDateTime lastAttempt;

    // Поле, чтобы понимать, какой воркер (поток) взял задачу
    private String processedBy;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.status = NotificationStatus.PENDING;
        this.attempts = 0;
    }
}