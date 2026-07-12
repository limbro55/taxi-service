package org.example.notification_service.controller;

import lombok.RequiredArgsConstructor;
import org.example.notification_service.entity.NotificationTask;
import org.example.notification_service.repository.NotificationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationRepository repository;

    @PostMapping
    public ResponseEntity<NotificationTask> createNotification(@RequestBody NotificationRequest request) {
        NotificationTask task = NotificationTask.builder()
                .tripId(request.getTripId())
                .message(request.getMessage())
                .build();

        return ResponseEntity.ok(repository.save(task));
    }

    @GetMapping
    public ResponseEntity<List<NotificationTask>> getNotificationsByTrip(@RequestParam("trip_id") Long tripId) {
        return ResponseEntity.ok(repository.findAllByTripId(tripId));
    }
}

// Простая DTO для запроса
class NotificationRequest {
    private Long tripId;
    private String message;

    // Getters/Setters или @Data
    public Long getTripId() { return tripId; }
    public String getMessage() { return message; }
}