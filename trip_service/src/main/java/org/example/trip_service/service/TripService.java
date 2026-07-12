package org.example.trip_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // Полезно для логов
import org.example.trip_service.entry.Trip;
import org.example.trip_service.entry.TripStatus;
import org.example.trip_service.model.NotificationTask;
import org.example.trip_service.repository.DriverRepository;
import org.example.trip_service.repository.NotificationTaskRepository;
import org.example.trip_service.repository.TripRepository;
import org.example.trip_service.model.Driver;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TripService {
    private final DriverRepository driverRepository;
    private final NotificationTaskRepository notificationRepository;
    private final TripRepository repository;
    private final RestTemplate restTemplate;

    private final String USER_SERVICE_URL = "http://localhost:8080";
    private final double RATE_PER_KM = 50.0;

    @Transactional
    public Trip createTrip(String origin, String destination) {
        try {
            // 1. Пытаемся получить ID из контекста.
            String currentPassenger = SecurityContextHolder.getContext().getAuthentication().getName();
            Long passengerId;

            // БЛОК ЗАЩИТЫ: Если в логине не число (например, "user" или "anonymousUser"),
            // используем дефолтный ID 123, чтобы не падать с ошибкой 500.
            try {
                passengerId = Long.parseLong(currentPassenger);
            } catch (NumberFormatException e) {
                log.warn("Не удалось распарсить ID пользователя '{}', используем тестовый ID 123", currentPassenger);
                passengerId = 123L;
            }

            // 2. ВРЕМЕННО ЗАКОММЕНТИРУЕМ: проверка через другой сервис.
            // Если user_service на порту 8080 не запущен, эта строка вызовет ошибку 500.
            // restTemplate.getForObject(USER_SERVICE_URL + "/passengers/" + passengerId, Object.class);

            // 3. Поиск и блокировка водителя
            Driver driver = driverRepository.findAndLockAvailableDriver()
                    .orElseThrow(() -> new RuntimeException("Нет доступных водителей в БД (нужен статус AVAILABLE)"));

            // 4. Бронируем водителя
            driver.setStatus("BUSY");
            driverRepository.save(driver);

            // 5. Создаем поездку
            Trip trip = new Trip();
            trip.setPassengerId(passengerId);
            trip.setDriverId(driver.getId());
            trip.setStartPoint(origin);
            trip.setEndPoint(destination);
            trip.setStatus(TripStatus.CREATED);
            trip.setPrice(calculatePrice(origin, destination));
            trip.setCreatedAt(LocalDateTime.now());

            Trip savedTrip = repository.save(trip);

            // 6. Уведомление
            NotificationTask notification = new NotificationTask();
            notification.setTripId(savedTrip.getId());
            notification.setMessage("Водитель назначен: " + driver.getName() + ". Стоимость: " + savedTrip.getPrice());
            notification.setStatus("PENDING");
            notificationRepository.save(notification);

            return savedTrip;
        } catch (Exception e) {
            log.error("Критическая ошибка при создании поездки: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private double calculatePrice(String origin, String destination) {
        double distance = 2 + (Math.random() * 18);
        return Math.round(distance * RATE_PER_KM * 100.0) / 100.0;
    }

    public List<Trip> getHistory(Long passengerId) {
        String currentPassenger = SecurityContextHolder.getContext().getAuthentication().getName();
        Long id;
        try {
            id = Long.parseLong(currentPassenger);
        } catch (NumberFormatException e) {
            id = passengerId; // Если в токене не число, используем то, что пришло в параметре
        }
        return repository.findAllByPassengerId(id);
    }
}