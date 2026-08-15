package com.routewise.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.routewise.dto.BudgetRequest;
import com.routewise.dto.BudgetResponse;
import com.routewise.security.JwtService;
import com.routewise.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budget")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;
    private final JwtService jwtService;

    @PostMapping("/{tripId}/generate")
    public BudgetResponse generateBudget(
            @PathVariable Long tripId,
            @RequestBody BudgetRequest request,
            @RequestHeader("Authorization")
            String authHeader
    ) throws JsonProcessingException {

        String token = authHeader.substring(7);

        String email =
                jwtService.extractEmail(token);

        return budgetService.generateBudget(
                tripId,
                email,
                request
        );
    }

    @GetMapping("/{tripId}")
    public BudgetResponse getBudget(
            @PathVariable Long tripId,
            @RequestHeader("Authorization")
            String authHeader
    ) {

        String token = authHeader.substring(7);

        String email =
                jwtService.extractEmail(token);

        return budgetService.getBudget(
                tripId,
                email
        );
    }
}
