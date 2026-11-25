package com.example.tripsync.expenses.Repository;

import com.example.tripsync.expenses.Entity.Expense;
import com.example.tripsync.expenses.Entity.ExpenseSplit;
import com.example.tripsync.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ExpenseSplitRepository extends JpaRepository<ExpenseSplit,Long> {
    List<ExpenseSplit> findByExpenseId(Long expenseId);
    List<ExpenseSplit> findByUserId(Long userId);
    List<ExpenseSplit> findByExpenseTripIdAndUserId(Long tripId,Long userId);

    Long user(User user);
}
