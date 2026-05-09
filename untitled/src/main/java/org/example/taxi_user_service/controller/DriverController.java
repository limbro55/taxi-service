package org.example.taxi_user_service.controller;

import lombok.RequiredArgsConstructor;
import org.example.taxi_user_service.entry.Driver;
import org.example.taxi_user_service.entry.DriverStatus;
import org.example.taxi_user_service.service.DriverService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService service;

    @PostMapping
    public Driver create(@RequestBody Driver driver) {
        return service.create(driver);
    }

    @PatchMapping("/{id}/status")
    public Driver updateStatus(@PathVariable Long id,
                               @RequestParam DriverStatus status) {
        return service.updateStatus(id, status);
    }

    @GetMapping("/available")
    public List<Driver> getAvailable() {
        return service.getAvailable();
    }
}
