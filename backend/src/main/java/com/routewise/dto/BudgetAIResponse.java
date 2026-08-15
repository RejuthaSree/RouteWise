package com.routewise.dto;

import lombok.Data;

@Data
public class BudgetAIResponse {
    private Double hotelCost;
    private Double foodCost;
    private Double transportCost;
    private Double activitiesCost;
    private Double miscellaneousCost;
    private Double totalCost;
}
