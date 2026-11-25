package com.example.tripsync.expenses.Controller;

import com.example.tripsync.expenses.Dtos.CreateExpenseRequest;
import com.example.tripsync.expenses.Dtos.CreateSettlementRequest;
import com.example.tripsync.expenses.Dtos.MemberBalanceResponse;
import com.example.tripsync.expenses.Services.BalanceService;
import com.example.tripsync.expenses.Services.ExpenseService;
import com.example.tripsync.expenses.Services.SettlementService;
import com.example.tripsync.trip.repository.TripMemberRepository;
import com.example.tripsync.user.entity.User;
import com.example.tripsync.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trips/{tripId}")
public class ExpenseController {
    private final ExpenseService expenseService;
    private final SettlementService settlementService;
    private final BalanceService balanceService;
    private final UserRepository userRepository;
    private final TripMemberRepository tripMemberRepository;
    @PostMapping("/expenses")
    public ResponseEntity<Void> createExpense(
            @PathVariable Long tripId,
            @Valid @RequestBody CreateExpenseRequest request,
            Authentication authentication
            ){
        User currentUser = getCurrentUser(authentication);
        expenseService.createExpense(tripId,currentUser.getId(),request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/balances")
    public ResponseEntity<List<MemberBalanceResponse>> getBalances(
            @PathVariable Long tripId,
            Authentication authentication
    ){
        User currentUser = getCurrentUser(authentication);
        if(!tripMemberRepository.existsByTripIdAndUserId(tripId,currentUser.getId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(balanceService.getBalance(tripId));
    }
    @PostMapping("/settlements")
    public ResponseEntity<Void> createSettlement(
            @PathVariable Long tripId,
            @Valid @RequestBody CreateSettlementRequest request,
            Authentication authentication
    ){
        User currentUser = getCurrentUser(authentication);
        settlementService.createSettlement(tripId,currentUser.getId(),request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    private User getCurrentUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getDeclaringClass().getName())
                .orElseThrow(()-> new IllegalArgumentException("Authenticated User not found"));
    }

}
