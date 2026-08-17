package com.routewise.controller;

import com.routewise.dto.*;
import com.routewise.security.JwtService;
import com.routewise.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;
    private final JwtService jwtService;

    @PostMapping("/{tripId}")
    public ExpenseResponse createExpense(
            @PathVariable Long tripId,
            @RequestBody ExpenseRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {

        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        return expenseService.createExpense(
                tripId,
                email,
                request
        );
    }

    @GetMapping("/{tripId}")
    public List<ExpenseResponse> getExpenses(
            @PathVariable Long tripId,
            @RequestHeader("Authorization") String authHeader
    ) {

        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        return expenseService.getExpenses(
                tripId,
                email
        );
    }

    @PutMapping("/{tripId}/{expenseId}")
    public ExpenseResponse updateExpense(
            @PathVariable Long tripId,
            @PathVariable Long expenseId,
            @RequestBody ExpenseRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {

        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        return expenseService.updateExpense(
                expenseId,
                tripId,
                email,
                request
        );
    }

    @DeleteMapping("/{tripId}/{expenseId}")
    public ResponseEntity<String> deleteExpense(
            @PathVariable Long tripId,
            @PathVariable Long expenseId,
            @RequestHeader("Authorization") String authHeader
    ) {

        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        expenseService.deleteExpense(
                expenseId,
                tripId,
                email
        );

        return ResponseEntity.ok("Expense deleted successfully");
    }

    @GetMapping("/{tripId}/summary")
    public ExpenseSummaryResponse getExpenseSummary(
            @PathVariable Long tripId,
            @RequestHeader("Authorization") String authHeader
    ) {

        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        return expenseService.getExpenseSummary(
                tripId,
                email
        );
    }

    @GetMapping("/{tripId}/compare-budget")
    public BudgetComparisonResponse compareBudget(
            @PathVariable Long tripId,
            @RequestHeader("Authorization") String authHeader
    ) {

        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        return expenseService.compareBudget(
                tripId,
                email
        );
    }
}
