package com.routewise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BudgetResponse {
    private String destination;
    private Integer travelers;
    private String budgetType;

    private Double hotelCost;
    private Double foodCost;
    private Double transportCost;
    private Double activitiesCost;
    private Double miscellaneousCost;
    private Double totalCost;
}
