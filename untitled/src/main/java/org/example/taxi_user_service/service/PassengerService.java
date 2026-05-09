package org.example.taxi_user_service.service;

import lombok.RequiredArgsConstructor;
import org.example.taxi_user_service.entry.Passenger;
import org.example.taxi_user_service.repository.PassengerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository repository;

    public Passenger create(Passenger passenger) {
        passenger.setCreatedAt(LocalDateTime.now());
        return repository.save(passenger);
    }

    public Passenger getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));
    }
}
