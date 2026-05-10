package org.example.taxi_user_service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class PassengerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testFullRegistrationApiFlow() throws Exception {
        String json = "{\"name\":\"Иван\", \"email\":\"ivan@mail.ru\", \"phone\":\"12345\"}";

        mockMvc.perform(post("/passengers") // Проверь свой путь в контроллере!
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Иван"));
    }

    @Test
    void shouldReturn400WhenEmailIsInvalid() throws Exception {
        String invalidJson = "{\"name\":\"Иван\", \"email\":\"not-an-email\", \"phone\":\"123\"}";

        mockMvc.perform(post("/passengers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest()); // Ждем ошибку 400
    }

    @Test
    void shouldReturn400WhenNameIsEmpty() throws Exception {
        // Отправляем пустую строку в имени и некорректный email
        String invalidPassenger = """
            {
                "name": "",
                "email": "not-an-email",
                "phone": ""
            }
            """;

        mockMvc.perform(post("/passengers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidPassenger))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Имя не может быть пустым"))
                .andExpect(jsonPath("$.email").value("Некорректный формат email"))
                .andExpect(jsonPath("$.phone").value("Телефон обязателен"));
    }

    @Test
    void shouldReturn404WhenPassengerNotFound() throws Exception {
        mockMvc.perform(get("/passengers/999"))
                .andExpect(status().isNotFound()) // Ждет код 404
                .andExpect(jsonPath("$.error").value("Passenger not found")); // Ждет это сообщение
    }


}