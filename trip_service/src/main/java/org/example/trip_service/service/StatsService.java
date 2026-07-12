package org.example.trip_service.service;

import lombok.RequiredArgsConstructor;
import org.example.trip_service.repository.TripRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatsService {
    private final TripRepository tripRepository;

    public Map<String, Object> getDailyStats() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();

        long count = tripRepository.countTripsToday(startOfDay);
        Double avgPrice = tripRepository.averagePriceToday(startOfDay);

        return Map.of(
                "trips_count", count,
                "average_price", avgPrice != null ? avgPrice : 0.0,
                "date", LocalDate.now().toString()
        );
    }
}