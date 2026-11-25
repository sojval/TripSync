package com.example.tripsync.expenses.Entity;

import com.example.tripsync.trip.entity.Trip;
import com.example.tripsync.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "trip_id",nullable = false)
    private Trip trip;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    private User paidBy;
    @Column(nullable = false,length = 150)
    private String title;
    @Column(nullable = false,length = 1000)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 150)
    private ExpensesCategory category;
    @Column(nullable = false,precision = 12,scale = 2)
    private BigDecimal amount;
    @Column(nullable = false)
    private LocalDate expenseDate;
}
