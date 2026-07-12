package org.example.trip_service.repository; // Твой путь к папке

import org.example.trip_service.model.Driver;
import org.example.trip_service.entry.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    @Query(value = "SELECT * FROM drivers WHERE status = 'AVAILABLE' LIMIT 1 FOR UPDATE", nativeQuery = true)
    Optional<Driver> findAndLockAvailableDriver();

}