package org.example.trip_service;

import org.example.trip_service.dto.DriverResponse;
import org.example.trip_service.entry.Trip;
import org.example.trip_service.repository.TripRepository;
import org.example.trip_service.service.TripService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TripServiceTest {

    @Mock
    private TripRepository repository; // Твой репозиторий

    @Mock
    private RestTemplate restTemplate; // Твой инструмент для запросов

    @InjectMocks
    private TripService tripService;

    @Test
    void shouldCreateTripSuccessfully() {
        // 1. Данные для теста
        Long passengerId = 1L;
        String origin = "Home";
        String destination = "Work";

        // 2. Настраиваем заглушки (Mocks)
        // Имитируем, что пассажир найден (возвращаем любой объект)
        when(restTemplate.getForObject(contains("/passengers/"), eq(Object.class)))
                .thenReturn(new Object());

        // Имитируем, что водитель найден
        DriverResponse mockDriver = new DriverResponse();
        mockDriver.setId(100L);
        when(restTemplate.getForObject(contains("/drivers/available-one"), eq(DriverResponse.class)))
                .thenReturn(mockDriver);

        // Имитируем сохранение в базу (возвращаем тот же объект, что пришел на вход)
        when(repository.save(any(Trip.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // 3. Выполняем метод
        Trip result = tripService.createTrip(passengerId, origin, destination);

        // 4. Проверяем результат
        assertNotNull(result);
        assertEquals(100L, result.getDriverId());
        assertEquals("Home", result.getStartPoint());
        assertEquals(500.0, result.getPrice());
    }

    @Test
    void shouldThrowExceptionWhenNoDriversFound() {
        // 1. Имитируем, что проверка пассажира прошла успешно
        // Используем contains, чтобы не привязываться к конкретному ID
        when(restTemplate.getForObject(contains("/passengers/"), eq(Object.class)))
                .thenReturn(new Object());

        // 2. Имитируем, что водитель НЕ найден (возвращаем null)
        // Используем contains для URL поиска водителя
        when(restTemplate.getForObject(contains("/drivers/available-one"), eq(DriverResponse.class)))
                .thenReturn(null);

        // 3. Проверяем, что вылетает ошибка
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            tripService.createTrip(1L, "A", "B");
        });

        // Проверяем, что в тексте ошибки есть нужная фраза
        assertTrue(exception.getMessage().contains("No drivers available"));
    }
}