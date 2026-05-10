package org.example.taxi_user_service.repository;

import org.example.taxi_user_service.entry.Driver;
import org.example.taxi_user_service.entry.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    List<Driver> findByStatus(DriverStatus status);
}
