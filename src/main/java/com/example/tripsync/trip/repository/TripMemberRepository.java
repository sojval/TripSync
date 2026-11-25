package com.example.tripsync.trip.repository;

import com.example.tripsync.trip.entity.Trip;
import com.example.tripsync.trip.entity.TripMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface TripMemberRepository extends JpaRepository<TripMembers,Long> {
    List<TripMembers> findByTripId(Long id);
    List<TripMembers> findByUserId(Long userId);
    Optional<TripMembers> findByTripAndUserId(Trip trip, Long user_id);
    boolean existsByTripIdAndUserId(Long tripId,Long userId);

    Long trip(Trip trip);
}
