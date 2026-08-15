package com.routewise.repository;

import com.routewise.entity.Itinerary;
import com.routewise.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItineraryRepository extends JpaRepository< Itinerary,Long> {

    Optional<Itinerary> findByTrip(Trip trip);
}
