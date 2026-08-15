package com.routewise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetEstimate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer travelers;
    private String budgetType;
    private Double hotelCost;
    private Double foodCost;
    private Double transportCost;
    private Double activitiesCost;
    private Double miscellaneousCost;
    private Double totalCost;

    @OneToOne
    @JoinColumn(name = "trip_id", unique = true)
    private Trip trip;
}
