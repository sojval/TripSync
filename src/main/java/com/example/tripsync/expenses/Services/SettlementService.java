package com.example.tripsync.expenses.Services;

import com.example.tripsync.expenses.Dtos.CreateSettlementRequest;
import com.example.tripsync.expenses.Entity.Settlement;
import com.example.tripsync.expenses.Repository.SettlementRepository;
import com.example.tripsync.trip.entity.Trip;
import com.example.tripsync.trip.repository.TripMemberRepository;
import com.example.tripsync.trip.repository.TripRepository;
import com.example.tripsync.user.entity.User;
import com.example.tripsync.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettlementService {
    private final SettlementRepository settlementRepository;
    private final TripRepository tripRepository;
    private final TripMemberRepository tripMemberRepository;
    private final UserRepository userRepository;
    @Transactional
    public Settlement createSettlement(
            Long tripId,
            Long payerId,
            CreateSettlementRequest request
    ){
        if(payerId.equals(request.payeeId())){
            throw new IllegalArgumentException("Payer and payee both cant be same");
        }
        Trip trip = tripRepository.findById(tripId).orElseThrow(
                ()->new IllegalArgumentException("Trip not found")
        );
        if(!tripMemberRepository.existsByTripIdAndUserId(tripId,payerId)){
            throw new IllegalArgumentException("Payer is not a member of this trip");
        }
        if(!tripMemberRepository.existsByTripIdAndUserId(tripId,request.payeeId())){
            throw new IllegalArgumentException("Payee is not a member of this trip");
        }
        User payer = userRepository.findById((payerId))
                .orElseThrow(()->new IllegalArgumentException("Payer not found"));
        User payee = userRepository.findById(request.payeeId())
                .orElseThrow(()->new IllegalArgumentException("Payee not found"));
        Settlement settlement = new Settlement();
        settlement.setTrip(trip);
        settlement.setPayee(payee);
        settlement.setPayer(payer);
        settlement.setAmount(request.amount());
        settlement.setNote(request.note());
        return settlementRepository.save(settlement);
    }
}
