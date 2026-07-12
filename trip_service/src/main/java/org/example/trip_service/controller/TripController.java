package org.example.trip_service.controller;

import lombok.RequiredArgsConstructor;
import org.example.trip_service.dto.TripRequest;
import org.example.trip_service.entry.Trip;
import org.example.trip_service.service.TripService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @PostMapping
    public ResponseEntity<Trip> createTrip(@RequestBody TripRequest request) {
        // Просто передаем поля "откуда" и "куда"
        return ResponseEntity.ok(tripService.createTrip(

                request.getOrigin(),
                request.getDestination()
        ));
    }

    @GetMapping
    public ResponseEntity<List<Trip>> getPassengerHistory(@RequestParam Long passengerId) {
        return ResponseEntity.ok(tripService.getHistory(passengerId));
    }
}