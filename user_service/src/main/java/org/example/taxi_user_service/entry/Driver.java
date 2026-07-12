package org.example.taxi_user_service.entry;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "drivers")
@Getter
@Setter
@NoArgsConstructor
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(name = "car_model")
    private String carModel;
    @Column(name = "car_number")
    private String carNumber;

    @Enumerated(EnumType.STRING)
    private DriverStatus status = DriverStatus.OFFLINE;

    private Double rating = 5.0;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
