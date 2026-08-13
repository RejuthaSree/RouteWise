package com.routewise.controller;

import com.routewise.entity.Trip;
import com.routewise.service.TripService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
@AllArgsConstructor
public class TripController {

    private final TripService tripService;

    @PostMapping
    public ResponseEntity<Trip> createTrip(
            @RequestBody Trip trip,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                tripService.createTrip( email,trip)
        );
    }

    @GetMapping
    public ResponseEntity<List<Trip>> getMyTrips(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                tripService.getMyTrips(email)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip>getTripById(@PathVariable Long id, Authentication authentication){
        String email = authentication.getName();

        return ResponseEntity.ok(
                tripService.getTripById(id,email)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip>updateTrip(@PathVariable Long id,@RequestBody Trip trip, Authentication authentication){
        String email = authentication.getName();

        return ResponseEntity.ok(
                tripService.updateTrip(id,trip,email)
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteTrip(@PathVariable Long id,Authentication authentication){
        String email = authentication.getName();
        tripService.deleteTrip(id,email);
        return ResponseEntity.ok("Trip deleted successfully!");
    }



}
