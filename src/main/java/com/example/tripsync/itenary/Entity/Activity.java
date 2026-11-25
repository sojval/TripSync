package com.example.tripsync.itenary.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Itinerary itinerary;
    @Column(nullable = false)
    private String title;
    @Column(length = 1000)
    private String description;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Column(precision = 12,scale = 2)
    private BigDecimal estimatedCost;
    @Column(nullable = false)
    private Integer displayOrder = 0;
}