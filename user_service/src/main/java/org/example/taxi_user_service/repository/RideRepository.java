package org.example.taxi_user_service.repository;

import org.example.taxi_user_service.entry.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findAllByPassengerId(Long passengerId);
}
