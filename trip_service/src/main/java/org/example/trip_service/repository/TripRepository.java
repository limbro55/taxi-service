package org.example.trip_service.repository;

import org.example.trip_service.entry.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    // Метод для получения истории поездок пассажира (как просили в таске)
    List<Trip> findAllByPassengerId(Long passengerId);
}