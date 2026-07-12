package org.example.trip_service.repository;

import org.example.trip_service.entry.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {

    @Query("SELECT COUNT(t) FROM Trip t WHERE t.createdAt >= :startOfDay")
    long countTripsToday(@Param("startOfDay") LocalDateTime startOfDay);

    // Используем Double, так как AVG может вернуть null, если записей нет
    @Query("SELECT AVG(t.price) FROM Trip t WHERE t.createdAt >= :startOfDay")
    Double averagePriceToday(@Param("startOfDay") LocalDateTime startOfDay);

    // TripRepository.java
    List<Trip> findAllByPassengerId(Long passengerId);
}