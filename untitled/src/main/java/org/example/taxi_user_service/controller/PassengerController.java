package org.example.taxi_user_service.controller;

import lombok.RequiredArgsConstructor;
import org.example.taxi_user_service.entry.Passenger;
import org.example.taxi_user_service.service.PassengerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService service;

    @PostMapping
    public Passenger create(@RequestBody Passenger passenger) {
        return service.create(passenger);
    }

    @GetMapping("/{id}")
    public Passenger get(@PathVariable Long id) {
        return service.getById(id);
    }
}
