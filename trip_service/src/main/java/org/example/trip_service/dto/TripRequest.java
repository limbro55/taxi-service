package org.example.trip_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link org.example.trip_service.entry.Trip}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripRequest {
    String startPoint;
    String endPoint;
}