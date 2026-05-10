package org.example.trip_service.dto;

import lombok.Data;

@Data
public class TripCreateRequest {
    private Long passengerId;
    private String origin;
    private String destination;
}