package com.example.tripsync.trip.entity;

import com.example.tripsync.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String Title;
    @Column(nullable = false)
    private String destination;

    private String description;
    @Column(nullable = false)
    private LocalDate startDate;
    private LocalDate endDate;
    @Column(precision = 12,scale = 2)
    private BigDecimal budget;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TripStatus status = TripStatus.PLANNED;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "owner_Id",nullable = false)
    private User owner;

}
