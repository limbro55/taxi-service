package org.example.trip_service.service;

import lombok.RequiredArgsConstructor;
import org.example.trip_service.dto.DriverResponse;
import org.example.trip_service.dto.TripCreateRequest;
import org.example.trip_service.entry.Trip;
import org.example.trip_service.entry.TripStatus;
import org.example.trip_service.repository.TripRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TripService {
    private final TripRepository repository;
    private final RestTemplate restTemplate;

    private final String USER_SERVICE_URL = "http://localhost:8080";

    public Trip createTrip(Long passengerId, String origin, String destination) {
        try {
            // 1. Проверка пассажира
            restTemplate.getForObject(USER_SERVICE_URL + "/passengers/" + passengerId, Object.class);

            // 2. Поиск водителя
            DriverResponse driver = restTemplate.getForObject(USER_SERVICE_URL + "/drivers/available-one", DriverResponse.class);

            if (driver == null) {
                throw new RuntimeException("No drivers available");
            }

            // 3. Создание и сохранение
            Trip trip = new Trip();
            trip.setPassengerId(passengerId);
            if (driver.getId() != null) {
                trip.setDriverId(driver.getId());
            }
            trip.setStartPoint(origin);
            trip.setEndPoint(destination);
            trip.setStatus(TripStatus.CREATED);

            // Добавим дефолтную цену, если в логике пока нет расчета
            trip.setPrice(500.0);

            trip.setCreatedAt(LocalDateTime.now());

            return repository.save(trip);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании поездки: " + e.getMessage());
        }
    }

    public List<Trip> getHistory(Long passengerId) {
        return repository.findAllByPassengerId(passengerId);
    }
}