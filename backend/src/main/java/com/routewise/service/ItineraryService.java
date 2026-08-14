package com.routewise.service;

import com.routewise.dto.ItineraryResponse;
import com.routewise.entity.Itinerary;
import com.routewise.entity.Trip;
import com.routewise.entity.User;
import com.routewise.exception.TripNotFoundException;
import com.routewise.exception.UserNotFoundException;
import com.routewise.repository.ItineraryRepository;
import com.routewise.repository.TripRepository;
import com.routewise.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ItineraryService {
    private final AIService aiService;
    private final ItineraryRepository itineraryRepository;
    private final UserRepository userRepository;
    private final TripRepository tripRepository;

    public ItineraryResponse generateItineraryResponse(String email,Long tripId){
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new UserNotFoundException("User not found"));

        Trip trip=tripRepository.findByIdAndUser(tripId,user)
                .orElseThrow(()-> new TripNotFoundException("Trip not found"));

        long days =
                java.time.temporal.ChronoUnit.DAYS.between(
                        trip.getStartDate().toLocalDate(),
                        trip.getEndDate().toLocalDate()
                );
        if(days<=0){
            days=1;
        }
        String generatedContent=aiService.generateItinerary(trip.getDestination(),days);

        Itinerary itinerary=itineraryRepository.findByTrip(trip)
                .orElse(new Itinerary());

        itinerary.setTrip(trip);
        itinerary.setContent(generatedContent);
        itineraryRepository.save(itinerary);

        return new ItineraryResponse(
                trip.getId(),
                trip.getDestination(),
                generatedContent
        );
    }
    public ItineraryResponse getItineraryResponse(Long tripId,String email){
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new UserNotFoundException("User not found"));

        Trip trip=tripRepository.findByIdAndUser(tripId,user)
                .orElseThrow(()-> new TripNotFoundException("Trip not found"));

        Itinerary itinerary=itineraryRepository.findByTrip(trip)
                .orElseThrow(()->new RuntimeException("Itinerary not found"));
        return new ItineraryResponse(
                trip.getId(),
                trip.getDestination(),
                itinerary.getContent()
        );
    }
}
