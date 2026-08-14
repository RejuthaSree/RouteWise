package com.routewise.service;

import com.routewise.dto.TripRequest;
import com.routewise.dto.TripResponse;
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

    public TripResponse createTrip(String email,TripRequest request){
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("user not found"));

        Trip trip=new Trip();
        trip.setTitle(request.getTitle());
        trip.setDestination(request.getDestination());
        trip.setStartDate(request.getStartDate());
        trip.setEndDate(request.getEndDate());

        trip.setUser(user);

        Trip savedTrip = tripRepository.save(trip);

        return new TripResponse(
                savedTrip.getId(),
                savedTrip.getTitle(),
                savedTrip.getDestination(),
                savedTrip.getStartDate(),
                savedTrip.getEndDate()
        );

    }
    public List<TripResponse> getMyTrips (String email){
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("user not found"));

        List<Trip>trips= tripRepository.findByUser(user);
        return trips.stream()
                .map(trip -> new TripResponse(
                        trip.getId(),
                        trip.getTitle(),
                        trip.getDestination(),
                        trip.getStartDate(),
                        trip.getEndDate()
                ))
                .toList();
    }

    public TripResponse getTripById(Long id,String email){
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("user not found"));

       Trip trip= tripRepository.findByIdAndUser(id,user)
                .orElseThrow(()->new RuntimeException("Trip not found"));

        return new TripResponse(
                trip.getId(),
                trip.getTitle(),
                trip.getDestination(),
                trip.getStartDate(),
                trip.getEndDate()
        );
    }

    public TripResponse updateTrip(Long id, TripRequest request, String email){
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("user not found"));

        Trip existingTrip=tripRepository.findByIdAndUser(id,user)
                .orElseThrow(()->new RuntimeException("trip not found"));

        existingTrip.setTitle(request.getTitle());
        existingTrip.setDestination(request.getDestination());
        existingTrip.setStartDate(request.getStartDate());
        existingTrip.setEndDate(request.getEndDate());

        Trip savedTrip = tripRepository.save(existingTrip);
        return new TripResponse(
                savedTrip.getId(),
                savedTrip.getTitle(),
                savedTrip.getDestination(),
                savedTrip.getStartDate(),
                savedTrip.getEndDate()
        );
    }

    public void deleteTrip(Long id,String email){
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("user not found"));

        Trip existingTrip=tripRepository.findByIdAndUser(id,user)
                .orElseThrow(()->new RuntimeException("trip not found"));

         tripRepository.delete(existingTrip);
    }
}
