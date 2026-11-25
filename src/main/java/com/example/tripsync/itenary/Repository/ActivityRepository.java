package com.example.tripsync.itenary.Repository;

import com.example.tripsync.itenary.Entity.Activity;
import com.example.tripsync.trip.entity.Trip;
import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Long> {
    List<Activity> findByItineraryIdOrderByDisplayOrderAsc(Long itineraryId);
    List<Activity> findByItineraryTripOrderByDisplayOrderAsc(Trip itinerary_trip);
}
