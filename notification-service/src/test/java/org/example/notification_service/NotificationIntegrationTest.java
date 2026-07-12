package org.example.notification_service;

import org.example.notification_service.entity.NotificationStatus;
import org.example.notification_service.entity.NotificationTask;
import org.example.notification_service.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@AutoConfigureMockMvc
public class NotificationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NotificationRepository repository;

    @BeforeEach
    void cleanUp() {
        repository.deleteAll();
    }

    @Test
    void shouldProcessNotificationAsync() throws Exception {
        // 1. Создаем уведомление через API
        String json = "{\"tripId\": 1, \"message\": \"Test notification\"}";

        mockMvc.perform(post("/api/v1/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        // 2. Ждем, пока фоновый воркер подхватит и отправит (SENT)
        await().atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
            var tasks = repository.findAllByTripId(1L);
            assertThat(tasks).isNotEmpty();
            assertThat(tasks.get(0).getStatus()).isEqualTo(NotificationStatus.SENT);
        });
    }

    @Test
    void shouldHandleManualTaskCreation() {
        // 1. Создаем задачу вручную (имитируем запись от Trip Service)
        NotificationTask task = NotificationTask.builder()
                .tripId(99L)
                .message("Manual task test")
                .status(NotificationStatus.PENDING)
                .attempts(0)
                .build();
        repository.save(task);

        // 2. Проверяем, что воркер видит её в базе и обрабатывает
        await().atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
            var updatedTask = repository.findAllByTripId(99L).get(0);
            // Проверяем, что воркер успешно перевел задачу в SENT
            assertThat(updatedTask.getStatus()).isEqualTo(NotificationStatus.SENT);
            assertThat(updatedTask.getProcessedBy()).isNotNull();
        });
    }
}