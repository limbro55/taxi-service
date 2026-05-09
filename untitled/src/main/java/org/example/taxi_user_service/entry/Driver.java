package org.example.taxi_user_service.entry;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;

    private String licenseNumber;

    @Enumerated(EnumType.STRING)
    private DriverStatus status;

    private LocalDateTime createdAt;
}
