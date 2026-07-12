package org.example.taxi_user_service.service;

import lombok.RequiredArgsConstructor;
import org.example.taxi_user_service.entry.Driver;
import org.example.taxi_user_service.entry.DriverStatus;
import org.example.taxi_user_service.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository repository;

    public Driver create(Driver driver) {
        driver.setStatus(DriverStatus.AVAILABLE);
        driver.setCreatedAt(LocalDateTime.now());
        return repository.save(driver);
    }

    public Driver updateStatus(Long id, DriverStatus status) {
        Driver driver = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        driver.setStatus(status);
        return repository.save(driver);
    }

    public Driver findAvailable() {
        return repository.findByStatus(DriverStatus.AVAILABLE)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No available drivers"));
    }

    public List<Driver> getAvailable() {
        return repository.findByStatus(DriverStatus.AVAILABLE);
    }
}
