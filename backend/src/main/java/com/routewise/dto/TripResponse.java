package com.routewise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class TripResponse {

private Long id;
private String title;
    private String Destination;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

}