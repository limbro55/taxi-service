package org.example.trip_service;

import org.example.trip_service.entry.Trip;
import org.example.trip_service.entry.TripStatus;
import org.example.trip_service.repository.TripRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class TripServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TripServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(TripRepository repository) {
        return args -> {
            Trip testTrip = new Trip();
            testTrip.setPassengerId(1L);
            testTrip.setStartPoint("Школа 21");
            testTrip.setEndPoint("Дом");
            testTrip.setStatus(TripStatus.CREATED);
            testTrip.setPrice(500.0); // Добавь это!
            testTrip.setDriverId(10L); // Добавь любое число для теста

            repository.save(testTrip);
        };
    }
}