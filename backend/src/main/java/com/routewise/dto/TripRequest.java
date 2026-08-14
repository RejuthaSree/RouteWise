package com.routewise.dto;


import lombok.Data;

import java.time.LocalDateTime;
@Data
public class TripRequest {


        private String title;


        private String Destination;

        private LocalDateTime startDate;

        private LocalDateTime endDate;

}
