package com.routewise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ItineraryResponse {
    private Long tripId;
    private String destination;
    private String Itinerary;


}
