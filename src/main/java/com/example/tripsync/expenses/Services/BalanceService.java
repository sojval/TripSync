package com.example.tripsync.expenses.Services;

import com.example.tripsync.expenses.Dtos.MemberBalanceResponse;
import com.example.tripsync.expenses.Entity.Expense;
import com.example.tripsync.expenses.Entity.ExpenseSplit;
import com.example.tripsync.expenses.Entity.Settlement;
import com.example.tripsync.expenses.Repository.ExpenseRepository;
import com.example.tripsync.expenses.Repository.ExpenseSplitRepository;
import com.example.tripsync.expenses.Repository.SettlementRepository;
import com.example.tripsync.trip.entity.TripMembers;
import com.example.tripsync.trip.repository.TripMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BalanceService {
    private final TripMemberRepository tripMemberRepository;
    private final ExpenseRepository expenseRepository;
    private final ExpenseSplitRepository expenseSplitRepository;
    private final SettlementRepository settlementRepository;
    @Transactional
    public List<MemberBalanceResponse> getBalance(Long tripId){
        List<TripMembers> members = tripMemberRepository.findByTripId(tripId);
        List<Expense> expenses = expenseRepository.findByTripIdOrderByExpenseDateDesc(tripId);
        List<Settlement> settlements = settlementRepository.findByTripIdOrderBySettledAtDesc(tripId);
        List<MemberBalanceResponse> results = new ArrayList<>();
        for(TripMembers members1 : members){
            Long userID = members1.getUser().getId();
            BigDecimal totalPaid = expenses
                    .stream()
                    .filter(
                    e->e.getPaidBy().getId().equals(userID))
                    .map(Expense::getAmount)
                    .reduce(BigDecimal.ZERO,BigDecimal::add);
            List<ExpenseSplit> splits = expenseSplitRepository.findByExpenseTripIdAndUserId(tripId,userID);
            BigDecimal totalShare = splits.stream()
                    .map(ExpenseSplit::getShareAmount)
                    .reduce(BigDecimal.ZERO,BigDecimal::add);
            BigDecimal settlementSent = settlements.stream()
                    .filter(s->s.getPayer().getId().equals(userID))
                    .map(Settlement::getAmount)
                    .reduce(BigDecimal.ZERO,BigDecimal::add);
            BigDecimal settlementReceived = settlements.stream()
                    .filter(s->s.getPayee().getId().equals(userID))
                    .map(Settlement::getAmount)
                    .reduce(BigDecimal.ZERO,BigDecimal::add);
            BigDecimal netBalance = totalPaid
                    .subtract(totalShare).add(settlementSent)
                    .subtract(settlementReceived);
            results.add(
                    new MemberBalanceResponse(
                            userID,
                            members1.getUser().getFullName(),
                            totalPaid,
                            totalShare,
                            settlementSent,
                            settlementReceived,
                            netBalance
                    ));

        }
        return results;
    }

}
