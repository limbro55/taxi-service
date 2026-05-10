package org.example.taxi_user_service.service;

import lombok.RequiredArgsConstructor;
import org.example.taxi_user_service.dto.PassengerRequest;
import org.example.taxi_user_service.entry.Passenger;
import org.example.taxi_user_service.exception.EntityNotFoundException;
import org.example.taxi_user_service.repository.PassengerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository repository;

    public Passenger getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found"));
    }

    public Passenger create(PassengerRequest request) {
        Passenger passenger = new Passenger();
        passenger.setName(request.getName());
        passenger.setEmail(request.getEmail());
        passenger.setPhone(request.getPhone());
        passenger.setCreatedAt(LocalDateTime.now());
        return repository.save(passenger);
    }
}
