package com.routewise.service;

import com.routewise.dto.BudgetComparisonResponse;
import com.routewise.dto.ExpenseRequest;
import com.routewise.dto.ExpenseResponse;
import com.routewise.dto.ExpenseSummaryResponse;
import com.routewise.entity.BudgetEstimate;
import com.routewise.entity.Expense;
import com.routewise.entity.Trip;
import com.routewise.entity.User;
import com.routewise.exception.TripNotFoundException;
import com.routewise.exception.UserNotFoundException;
import com.routewise.repository.BudgetEstimateRepository;
import com.routewise.repository.ExpenseRepository;
import com.routewise.repository.TripRepository;
import com.routewise.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExpenseService {
    private final UserRepository userRepository;
    private final TripRepository tripRepository;
    private final ExpenseRepository expenseRepository;
    private final BudgetEstimateRepository budgetEstimateRepository;

    public ExpenseResponse createExpense(Long tripId , String email , ExpenseRequest request){

        User user =userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("user not found"));

        Trip trip=tripRepository.findByIdAndUser(tripId,user)
                .orElseThrow(()->new TripNotFoundException("Trip not found"));

        Expense expense=new Expense();
        expense.setAmount(request.getAmount());
        expense.setTitle(request.getTitle());
        expense.setDate(request.getDate());
        expense.setCategory(request.getCategory());
        expense.setTrip(trip);

        Expense saved = expenseRepository.save(expense);
        return new ExpenseResponse(
                saved.getId(),
                saved.getAmount(),
                saved.getTitle(),
                saved.getCategory(),
                saved.getDate()
        );
    }

    public List<ExpenseResponse> getExpenses(Long tripId,String email){
        User user =userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("user not found"));

        Trip trip=tripRepository.findByIdAndUser(tripId,user)
                .orElseThrow(()->new TripNotFoundException("Trip not found"));

        List<Expense> expenses =expenseRepository.findByTrip(trip);
        return expenses.stream()
                .map(expense ->
                        new ExpenseResponse(
                                expense.getId(),
                                expense.getAmount(),
                                expense.getTitle(),
                                expense.getCategory(),
                                expense.getDate()
                        ))
                .toList();
    }

    public ExpenseResponse updateExpense(
            Long expenseId,
            Long tripId,
            String email,
            ExpenseRequest request
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        Trip trip = tripRepository.findByIdAndUser(tripId, user)
                .orElseThrow(() ->
                        new TripNotFoundException("Trip not found"));

        Expense expense =
                expenseRepository.findByIdAndTrip(
                                expenseId,
                                trip
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Expense not found"
                                ));

        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDate(request.getDate());

        Expense updated =
                expenseRepository.save(expense);

        return new ExpenseResponse(
                updated.getId(),
                updated.getAmount(),
                updated.getTitle(),
                updated.getCategory(),
                updated.getDate()
        );
    }
    public void deleteExpense(
            Long expenseId,
            Long tripId,
            String email
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        Trip trip = tripRepository.findByIdAndUser(tripId, user)
                .orElseThrow(() ->
                        new TripNotFoundException("Trip not found"));

        Expense expense =
                expenseRepository.findByIdAndTrip(
                                expenseId,
                                trip
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Expense not found"
                                ));

        expenseRepository.delete(expense);
    }
    public ExpenseSummaryResponse getExpenseSummary(
            Long tripId,
            String email
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        Trip trip = tripRepository.findByIdAndUser(tripId, user)
                .orElseThrow(() ->
                        new TripNotFoundException("Trip not found"));

        List<Expense> expenses =
                expenseRepository.findByTrip(trip);

        double totalSpent = 0;
        double hotel = 0;
        double food = 0;
        double transport = 0;
        double activities = 0;
        double shopping = 0;
        double other = 0;

        for (Expense expense : expenses) {

            totalSpent += expense.getAmount();

            switch (expense.getCategory().toLowerCase()) {

                case "hotel":
                    hotel += expense.getAmount();
                    break;

                case "food":
                    food += expense.getAmount();
                    break;

                case "transport":
                    transport += expense.getAmount();
                    break;

                case "activities":
                    activities += expense.getAmount();
                    break;

                case "shopping":
                    shopping += expense.getAmount();
                    break;

                default:
                    other += expense.getAmount();
            }
        }

        return new ExpenseSummaryResponse(
                totalSpent,
                hotel,
                food,
                transport,
                activities,
                shopping,
                other
        );
    }
    public BudgetComparisonResponse compareBudget(
            Long tripId,
            String email
    ) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        Trip trip = tripRepository.findByIdAndUser(tripId, user)
                .orElseThrow(() ->
                        new TripNotFoundException("Trip not found"));

        BudgetEstimate budget =
                budgetEstimateRepository.findByTrip(trip)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Budget not found"
                                ));

        List<Expense> expenses =
                expenseRepository.findByTrip(trip);

        double actualSpent = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        double remaining =
                budget.getTotalCost() - actualSpent;

        return new BudgetComparisonResponse(
                budget.getTotalCost(),
                actualSpent,
                remaining
        );
    }
}
