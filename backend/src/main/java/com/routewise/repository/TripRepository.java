package com.routewise.repository;

import com.routewise.entity.Trip;
import com.routewise.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip,Long> {

    List<Trip> findByUser(User user);

    Optional<Trip> findByIdAndUser(Long id,User user);
}
