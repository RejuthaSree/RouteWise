package com.routewise.repository;

import com.routewise.entity.BudgetEstimate;
import com.routewise.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BudgetEstimateRepository extends JpaRepository<BudgetEstimate,Long> {

    Optional<BudgetEstimate> findByTrip(Trip trip);
}
