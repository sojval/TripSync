package com.example.tripsync.expenses.Entity;

import com.example.tripsync.common.Entity.BaseEntity;
import com.example.tripsync.trip.entity.Trip;
import com.example.tripsync.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
public class Settlement{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "trip_id",nullable = false)
    private Trip trip;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "payer_id",nullable = false)
    private User payer;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "payee_id",nullable = false)
    private User payee;
    @Column(nullable = false,precision = 12,scale = 2)
    private BigDecimal amount;
    @Column(nullable = false)
    private LocalDateTime settledAt;
    @Column(length = 500)
    private String note;
}
