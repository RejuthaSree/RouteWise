package com.routewise.controller;

import com.routewise.dto.TripRequest;
import com.routewise.dto.TripResponse;
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
    public ResponseEntity<TripResponse> createTrip(
            @RequestBody TripRequest tripRequest,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                tripService.createTrip( email,tripRequest)
        );
    }

    @GetMapping
    public ResponseEntity<List<TripResponse>> getMyTrips(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                tripService.getMyTrips(email)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripResponse>getTripById(@PathVariable Long id, Authentication authentication){
        String email = authentication.getName();

        return ResponseEntity.ok(
                tripService.getTripById(id,email)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<TripResponse>updateTrip(@PathVariable Long id,@RequestBody TripRequest tripRequest, Authentication authentication){
        String email = authentication.getName();

        return ResponseEntity.ok(
                tripService.updateTrip(id,tripRequest,email)
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteTrip(@PathVariable Long id,Authentication authentication){
        String email = authentication.getName();
        tripService.deleteTrip(id,email);
        return ResponseEntity.ok("Trip deleted successfully!");
    }



}
