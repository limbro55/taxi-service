package org.example.taxi_user_service.repository;

import org.example.taxi_user_service.entry.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
