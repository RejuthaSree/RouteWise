package com.routewise.repository;

import com.routewise.entity.Expense;
import com.routewise.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {

    List<Expense> findByTrip(Trip trip);

    Optional<Expense> findByIdAndTrip(Long id,Trip trip);
}
