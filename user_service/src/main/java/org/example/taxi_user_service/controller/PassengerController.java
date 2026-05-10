package org.example.taxi_user_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.taxi_user_service.dto.PassengerRequest;
import org.example.taxi_user_service.entry.Passenger;
import org.example.taxi_user_service.service.PassengerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService service;

    @GetMapping("/{id}")
    public Passenger get(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Passenger create(@Valid @RequestBody PassengerRequest request) {
        return service.create(request);
    }
}
