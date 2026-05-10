package org.example.trip_service.entry;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "trips")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "passenger_id", nullable = false)
    private Long passengerId;

    @Column(name = "driver_id")
    private Long driverId;

    @Column(name = "start_point", nullable = false)
    private String startPoint; // Соответствует start_point в SQL

    @Column(name = "end_point", nullable = false)
    private String endPoint;   // Соответствует end_point в SQL

    @Enumerated(EnumType.STRING)
    private TripStatus status;

    @Column(name = "price")
    private Double price;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}