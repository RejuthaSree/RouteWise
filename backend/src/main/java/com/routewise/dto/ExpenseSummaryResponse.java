package com.routewise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ExpenseSummaryResponse {

    private Double TotalSpent;
    private  Double Hotel;
    private Double food;
    private Double transport;
    private Double activities;
    private Double Shopping;
    private Double other;

}
