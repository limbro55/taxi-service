package org.example.taxi_user_service.service;

import lombok.RequiredArgsConstructor;
import org.example.taxi_user_service.entry.Driver;
import org.example.taxi_user_service.entry.DriverStatus;
import org.example.taxi_user_service.entry.Ride;
import org.example.taxi_user_service.entry.RideStatus;
import org.example.taxi_user_service.repository.DriverRepository;
import org.example.taxi_user_service.repository.PassengerRepository;
import org.example.taxi_user_service.repository.RideRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;
    private final PassengerRepository passengerRepository;
    private final DriverRepository driverRepository;

    @Transactional
    public Ride createRide(Long passengerId, String startPoint, String endPoint) {
        // 1. Ищем пассажира
        var passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));

        // 2. Ищем свободного водителя (берем первого попавшегося)
        Driver availableDriver = driverRepository.findByStatus(DriverStatus.AVAILABLE)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No available drivers found!"));

        // 3. Создаем поездку
        Ride ride = new Ride();
        ride.setPassenger(passenger);
        ride.setDriver(availableDriver);
        ride.setStartPoint(startPoint);
        ride.setEndPoint(endPoint);
        ride.setStatus(RideStatus.CREATED);
        ride.setFare(BigDecimal.valueOf(250.0)); // Пока захардкодим цену
        ride.setCreatedAt(LocalDateTime.now());

        // 4. Меняем статус водителю, чтобы он больше не был доступен другим
        availableDriver.setStatus(DriverStatus.ON_RIDE);
        driverRepository.save(availableDriver);

        return rideRepository.save(ride);
    }
}