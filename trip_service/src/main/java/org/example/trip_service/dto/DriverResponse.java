package org.example.trip_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DriverResponse {
    @JsonProperty("id")
    private Long id;
    private String name;
    private String status;
}