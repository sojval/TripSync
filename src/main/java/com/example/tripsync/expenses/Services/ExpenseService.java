package com.example.tripsync.expenses.Services;

import com.example.tripsync.expenses.Dtos.CreateExpenseRequest;
import com.example.tripsync.expenses.Dtos.ExpenseSplitRequest;
import com.example.tripsync.expenses.Entity.Expense;
import com.example.tripsync.expenses.Entity.ExpenseSplit;
import com.example.tripsync.expenses.Repository.ExpenseRepository;
import com.example.tripsync.expenses.Repository.ExpenseSplitRepository;
import com.example.tripsync.trip.entity.Trip;
import com.example.tripsync.trip.repository.TripMemberRepository;
import com.example.tripsync.trip.repository.TripRepository;
import com.example.tripsync.user.entity.User;
import com.example.tripsync.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ExpenseSplitRepository expenseSplitRepository;
    private final TripRepository tripRepository;
    private final TripMemberRepository tripMemberRepository;
    private final UserRepository userRepository;
    @Transactional
    public Expense createExpense(Long tripId, Long paidByUserId, CreateExpenseRequest request){
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new IllegalArgumentException("trip not found"));
        if(!tripMemberRepository.existsByTripIdAndUserId(tripId,paidByUserId)){
            throw new IllegalArgumentException("Payement user is not trip memeber");
        }
        BigDecimal splitTotal = BigDecimal.ZERO;
        Set<Long> userIds = new HashSet<>();
        for(ExpenseSplitRequest splitRequest: request.splits()){
            if(splitRequest.shareAmount().signum()<0){
                throw new IllegalArgumentException("split amount cannot be negative");
            }
            if(!userIds.add(splitRequest.userId())){
                throw new IllegalArgumentException("Duplicate user in expense Splits");
            }
            if(!tripMemberRepository.existsByTripIdAndUserId(tripId,splitRequest.userId())){
                throw new IllegalArgumentException("split user is not trip member");

            }
            splitTotal = splitTotal.add(splitRequest.shareAmount());
        }
        if(splitTotal.compareTo(request.amount()) != 0){
            throw new IllegalArgumentException("split total must equal expense Amount");
        }
        User paidBy = userRepository.findById(paidByUserId)
                .orElseThrow(()->
                        new IllegalArgumentException("User not found"));

        Expense expense = new Expense();
        expense.setTrip(trip);
        expense.setPaidBy(paidBy);
        expense.setTitle(request.title());
        expense.setDescription(request.description());
        expense.setCategory(request.category());
        expense.setAmount(request.amount());
        expense.setExpenseDate(request.expenseDate());
        Expense savedExpense = expenseRepository.save(expense);
        for(ExpenseSplitRequest splitRequest:request.splits()){
            User splitUser = userRepository.findById(splitRequest.userId()).orElseThrow(
                    ()-> new IllegalArgumentException("User not found")
            );
            ExpenseSplit expenseSplit = new ExpenseSplit();
            expenseSplit.setExpense(savedExpense);
            expenseSplit.setUser(splitUser);
            expenseSplit.setShareAmount(splitRequest.shareAmount());
            expenseSplitRepository.save(expenseSplit);
        }
        return savedExpense;
    }
    // Net balance = Paid amount - share Amount + Settelement sent - settelements Recieved

}
