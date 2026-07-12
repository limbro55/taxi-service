package org.example.trip_service.model; // Проверь свой путь

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "drivers")
@Data // Если используешь Lombok, он сам создаст геттеры и сеттеры
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String carModel;

    // Статус может быть: AVAILABLE, BUSY, OFFLINE
    private String status;

    // Конструктор по умолчанию нужен для Hibernate
    public Driver() {}
}