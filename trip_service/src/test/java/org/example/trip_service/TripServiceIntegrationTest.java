package org.example.trip_service;

import org.example.trip_service.entry.Trip;
import org.example.trip_service.model.Driver;
import org.example.trip_service.repository.DriverRepository;
import org.example.trip_service.repository.NotificationTaskRepository;
import org.example.trip_service.service.TripService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser; // Добавь импорт
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class TripServiceIntegrationTest {

    @Autowired
    private TripService tripService;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private NotificationTaskRepository notificationRepository;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    @WithMockUser(username = "1") // Имитируем авторизованного пассажира с ID 1
    void testFullFlowWithDatabase() {
        // 1. Имитируем успешный ответ от User Service
        when(restTemplate.getForObject(anyString(), eq(Object.class)))
                .thenReturn(new Object());

        // 2. Создаем доступного водителя в БД
        Driver driver = new Driver();
        driver.setName("Test Driver");
        driver.setStatus("AVAILABLE");
        driverRepository.save(driver);

        // 3. Вызываем сервис (теперь он найдет пользователя "1" в контексте)
        Trip trip = tripService.createTrip("Point A", "Point B");

        // 4. Проверки
        assertNotNull(trip.getId());
        assertEquals(1L, trip.getPassengerId()); // Проверяем, что ID подставился из контекста

        // Проверяем статус водителя
        Driver savedDriver = driverRepository.findById(driver.getId()).orElseThrow();
        assertEquals("BUSY", savedDriver.getStatus());

        // Проверяем уведомление
        assertFalse(notificationRepository.findAll().isEmpty());
    }
}