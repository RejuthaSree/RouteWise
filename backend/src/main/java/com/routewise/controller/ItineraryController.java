package com.routewise.controller;

import com.routewise.dto.ItineraryResponse;
import com.routewise.security.JwtService;
import com.routewise.service.ItineraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/itineraries")
@RequiredArgsConstructor
public class ItineraryController {

    private final ItineraryService itineraryService;
    private final JwtService jwtService;

    @PostMapping("/{tripId}/generate")
    public ItineraryResponse generate(
            @PathVariable Long tripId,
            @RequestHeader("Authorization")
            String authHeader){

        String token =
                authHeader.substring(7);

        String email =
                jwtService.extractEmail(token);

        return itineraryService.generateItineraryResponse(

                email,
                tripId
        );
    }

    @GetMapping("/{tripId}")
    public ItineraryResponse getItinerary(
            @PathVariable Long tripId,
            @RequestHeader("Authorization")
            String authHeader){

        String token =
                authHeader.substring(7);

        String email =
                jwtService.extractEmail(token);

        return itineraryService.getItineraryResponse(
                tripId,
                email
        );
    }
}
