package org.example.taxi_user_service;

import org.example.taxi_user_service.entry.Passenger;
import org.example.taxi_user_service.repository.PassengerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
// Эта аннотация говорит Spring НЕ подменять твой Postgres на базу в памяти (H2),
// чтобы мы тестировали именно на реальном окружении
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PassengerRepositoryTest {

    @Autowired
    private PassengerRepository passengerRepository;

    @Test
    public void shouldSaveAndFindPassenger() {
        // Arrange (Подготовка данных)
        Passenger passenger = new Passenger();
        passenger.setName("Ivan");
        passenger.setEmail("ivan@example.com");
        passenger.setPhone("79991234567");

        // Act (Действие)
        Passenger saved = passengerRepository.save(passenger);
        Passenger found = passengerRepository.findById(saved.getId()).orElse(null);

        // Assert (Проверка)
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Ivan");
        assertThat(found.getEmail()).isEqualTo("ivan@example.com");
    }
}