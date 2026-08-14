package com.routewise.repository;

import com.routewise.entity.Itinerary;
import com.routewise.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItineraryRepository extends JpaRepository< Itinerary,Long> {

    Optional<Itinerary> findByTrip(Trip trip);
}
