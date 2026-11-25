package com.example.tripsync.itenary.Entity;

import com.example.tripsync.trip.entity.Trip;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Itinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "trip_id",nullable = false)
    private Trip trip;
    @Column(nullable = false)
    private LocalDate itineraryDate;
    @Column(length = 1000)
    private String notes;

}
