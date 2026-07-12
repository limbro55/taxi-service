package org.example.trip_service;

import org.example.trip_service.controller.TripController;
import org.example.trip_service.repository.TripRepository;
import org.example.trip_service.service.TripService;
import org.example.trip_service.security.JwtUtils; // Добавь импорт своего JwtUtils
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser; // Нужно для авторизации
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf; // Нужно для CSRF
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(TripController.class)
public class TripControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TripService tripService;

    @MockBean
    private TripRepository tripRepository;

    @MockBean
    private JwtUtils jwtUtils; // Заглушка, чтобы JwtFilter не ругался при запуске

    @Test
    @WithMockUser(username = "1") // Притворяемся залогиненным пользователем
    void shouldReturnOk() throws Exception {
        // Обновленный JSON с новыми полями origin/destination
        String json = "{\"origin\": \"A\", \"destination\": \"B\"}";

        mockMvc.perform(post("/api/v1/trips")
                        .with(csrf()) // Добавляем CSRF токен в запрос
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }
}