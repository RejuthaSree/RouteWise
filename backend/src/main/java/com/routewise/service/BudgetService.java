package com.routewise.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.routewise.dto.BudgetAIResponse;
import com.routewise.dto.BudgetRequest;
import com.routewise.dto.BudgetResponse;
import com.routewise.entity.BudgetEstimate;
import com.routewise.entity.Trip;
import com.routewise.entity.User;
import com.routewise.exception.TripNotFoundException;
import com.routewise.exception.UserNotFoundException;
import com.routewise.repository.BudgetEstimateRepository;
import com.routewise.repository.TripRepository;
import com.routewise.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class BudgetService {
    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final BudgetEstimateRepository budgetEstimateRepository;
    private final AIService aiService;
    private final ObjectMapper objectMapper;

    public BudgetResponse generateBudget(Long id, String email, BudgetRequest request) throws JsonProcessingException {
        User user=userRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        Trip trip=tripRepository.findByIdAndUser(id,user)
                .orElseThrow(()->new TripNotFoundException("Trip not found"));

        long days = ChronoUnit.DAYS.between(
                trip.getStartDate().toLocalDate(),
                trip.getEndDate().toLocalDate()
        );
        String aiResponse =
                aiService.generateBudgetEstimate(
                        trip.getDestination(),
                        days,
                        request.getTravelers(),
                        request.getBudgetType()
                );
        if(days <= 0){
            days = 1;
        }
        BudgetAIResponse budget =
                objectMapper.readValue(
                        aiResponse,
                        BudgetAIResponse.class
                );
        BudgetEstimate estimate =
                budgetEstimateRepository.findByTrip(trip)
                        .orElse(new BudgetEstimate());

        estimate.setTrip(trip);
        estimate.setTravelers(request.getTravelers());
        estimate.setBudgetType(request.getBudgetType());
        estimate.setHotelCost(budget.getHotelCost());
        estimate.setFoodCost(budget.getFoodCost());
        estimate.setTransportCost(budget.getTransportCost());
        estimate.setActivitiesCost(budget.getActivitiesCost());
        estimate.setMiscellaneousCost(
                budget.getMiscellaneousCost()
        );
        estimate.setTotalCost(budget.getTotalCost());

        budgetEstimateRepository.save(estimate);
        return new BudgetResponse(
                trip.getDestination(),
                request.getTravelers(),
                request.getBudgetType(),
                budget.getHotelCost(),
                budget.getFoodCost(),
                budget.getTransportCost(),
                budget.getActivitiesCost(),
                budget.getMiscellaneousCost(),
                budget.getTotalCost()
        );
    }
    public BudgetResponse getBudget(
            Long tripId,
            String email
    )
    {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        Trip trip = tripRepository.findByIdAndUser(tripId, user)
                .orElseThrow(() ->
                        new TripNotFoundException("Trip not found"));

        BudgetEstimate estimate =
                budgetEstimateRepository.findByTrip(trip)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Budget estimate not found"
                                ));
        return new BudgetResponse(
                trip.getDestination(),
                estimate.getTravelers(),
                estimate.getBudgetType(),
                estimate.getHotelCost(),
                estimate.getFoodCost(),
                estimate.getTransportCost(),
                estimate.getActivitiesCost(),
                estimate.getMiscellaneousCost(),
                estimate.getTotalCost()
        );
    }
}
