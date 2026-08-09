package com.routewise.service;

import com.routewise.entity.Trip;
import com.routewise.entity.User;
import com.routewise.repository.TripRepository;
import com.routewise.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class TripService {
    private final UserRepository userRepository;
    private final TripRepository tripRepository;

    public Trip createTrip(String email,Trip trip){
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("user not found"));

        trip.setUser(user);

        return tripRepository.save(trip);

    }
    public List<Trip> getMyTrips (String email){
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("user not found"));

        return tripRepository.findByUser(user);
    }
}
