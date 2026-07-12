package org.example.trip_service;

import org.example.trip_service.entry.Trip;
import org.example.trip_service.model.Driver;
import org.example.trip_service.repository.DriverRepository;
import org.example.trip_service.repository.NotificationTaskRepository;
import org.example.trip_service.repository.TripRepository;
import org.example.trip_service.service.TripService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TripServiceTest {

    @Mock
    private TripRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TripService tripService;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private NotificationTaskRepository notificationRepository;

    @BeforeEach
    void setUp() {
       UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken("1", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void shouldCreateTripSuccessfully() {
        // 1. Данные
        String origin = "Home";
        String destination = "Work";

        // 2. Настройки Mocks
        // УДАЛИ ИЛИ ЗАКОММЕНТИРУЙ ЭТО, так как в сервисе вызов закомментирован:
        // when(restTemplate.getForObject(anyString(), eq(Object.class))).thenReturn(new Object());

        Driver mockDriver = new Driver();
        mockDriver.setId(100L);
        mockDriver.setName("Ivan");
        mockDriver.setStatus("AVAILABLE");

        when(driverRepository.findAndLockAvailableDriver())
                .thenReturn(Optional.of(mockDriver));

        when(repository.save(any(Trip.class))).thenAnswer(i -> i.getArgument(0));
        when(notificationRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        // 3. Выполняем
        Trip result = tripService.createTrip(origin, destination);
        // 4. Проверяем
        assertNotNull(result);
        assertEquals(100L, result.getDriverId());
        // Проверяем, что ID подтянулся из SecurityContext
        assertEquals(1L, result.getPassengerId());
        assertEquals("BUSY", mockDriver.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenNoDriversFound() {
        // Настройка мока остается прежней
        when(driverRepository.findAndLockAvailableDriver())
                .thenReturn(Optional.empty());

        // Выполняем
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            tripService.createTrip("A", "B");
        });

        // ИСПРАВЬ ЗДЕСЬ: текст должен совпадать с тем, что в TripService.java
        assertTrue(exception.getMessage().contains("Нет доступных водителей в БД"));
    }
}