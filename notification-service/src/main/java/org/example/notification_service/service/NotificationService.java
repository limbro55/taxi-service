package org.example.notification_service.service;

import lombok.RequiredArgsConstructor;
import org.example.notification_service.entity.NotificationTask;
import org.example.notification_service.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;

    @Transactional
    public NotificationTask createNotification(Long tripId, String message) {
        NotificationTask task = NotificationTask.builder()
                .tripId(tripId)
                .message(message)
                .build();
        return repository.save(task);
    }

    public List<NotificationTask> getHistory(Long tripId) {
        return repository.findAllByTripId(tripId);
    }
}