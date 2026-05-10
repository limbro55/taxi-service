package org.example.trip_service.dto;

import lombok.Data;

@Data
public class DriverDTO {
    private Long id;
    private String name;
    private String status; // Сюда прилетит строка "AVAILABLE"
}