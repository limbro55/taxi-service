package org.example.trip_service.controller;

import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Trip> createTrip(@RequestBody Trip tripRequest) {
        return ResponseEntity.ok(tripService.createTrip(
                tripRequest.getPassengerId(),
                tripRequest.getStartPoint(),
                tripRequest.getEndPoint()
        ));
    }

    @GetMapping
    public ResponseEntity<List<Trip>> getPassengerHistory(@RequestParam Long passengerId) {
        return ResponseEntity.ok(tripService.getHistory(passengerId));
    }
}