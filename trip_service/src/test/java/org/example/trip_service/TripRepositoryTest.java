package org.example.trip_service;

import org.example.trip_service.entry.Trip;
import org.example.trip_service.entry.TripStatus;
import org.example.trip_service.repository.TripRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Используем твой PostgreSQL
public class TripRepositoryTest {

    @Autowired
    private TripRepository repository;

    @Test
    void shouldSaveAndFindTrip() {
        Trip trip = new Trip();
        // 1. Устанавливаем ID пассажира (обязательно!)
        trip.setPassengerId(1L);

        // 2. Устанавливаем точки (у тебя они уже были)
        trip.setStartPoint("Point A");
        trip.setEndPoint("Point B");

        // 3. Статус
        trip.setStatus(TripStatus.CREATED);

        // 4. Цена (если в Trip это Double, пиши 150.0)
        trip.setPrice(150.0);

        // Теперь сохранение пройдет успешно
        Trip saved = repository.save(trip);

        assertNotNull(saved.getId());
        assertEquals("Point A", repository.findById(saved.getId()).get().getStartPoint());
    }
}