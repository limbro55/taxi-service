package org.example.trip_service;

import org.example.trip_service.model.Driver;
import org.example.trip_service.repository.DriverRepository;
import org.example.trip_service.repository.NotificationTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser; // Добавь это
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf; // И это
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class TripControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private NotificationTaskRepository notificationRepository;

    @MockBean
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        notificationRepository.deleteAll();
        driverRepository.deleteAll();

        Driver driver = new Driver();
        driver.setName("Professional Driver");
        driver.setStatus("AVAILABLE");
        driverRepository.save(driver);

        // Важно: мокаем внешний вызов User Service, иначе будет 500 ошибка
        when(restTemplate.getForObject(anyString(), eq(Object.class)))
                .thenReturn(new Object());
    }

    @Test
    @WithMockUser(username = "1") // Притворяемся пользователем с ID 1
    public void testCreateTripEndpoint() throws Exception {
        // Теперь в JSON не нужен passengerId, сервис возьмет его из "username" выше
        String tripJson = """
            {
                "origin": "Center",
                "destination": "Airport"
            }
            """;

        mockMvc.perform(post("/api/v1/trips")
                        .with(csrf()) // Добавляем CSRF токен, чтобы не было 403
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tripJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.passengerId").value(1));
    }
}