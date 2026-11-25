package com.example.tripsync.itenary.Repository;

import com.example.tripsync.itenary.Entity.Itinerary;
import com.example.tripsync.trip.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary,Long> {
    List<Itinerary> findByTripOrderByItineraryDateAsc(Trip trip);
    Optional<Itinerary> findByTripIdAndItineraryDate(Long tripId, LocalDate itineraryDate);
    boolean existsByTripIdAndItineraryDate(Long tripId, LocalDate itineraryDate);
}
