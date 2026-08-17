package com.routewise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetComparisonResponse {

    private Double estimatedBudget;

    private Double actualSpent;

    private Double remainingBudget;
}