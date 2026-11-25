package com.example.tripsync.expenses.Repository;

import com.example.tripsync.expenses.Entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {
    List<Expense> findByTripIdOrderByExpenseDateDesc(Long tripId);
    Optional<Expense> findByIdAndTripId(Long id, Long tripId);
    List<Expense> findByPaidByIdAndTripId(Long userId,Long tripId);
}
