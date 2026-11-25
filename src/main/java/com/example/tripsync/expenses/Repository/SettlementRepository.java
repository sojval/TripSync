package com.example.tripsync.expenses.Repository;

import com.example.tripsync.expenses.Entity.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SettlementRepository extends JpaRepository<Settlement,Long> {
    List<Settlement> findByTripIdOrderBySettledAtDesc(Long tripId);
    List<Settlement> findByTripIdAndPayerId(Long tripId,Long payerId);
    List<Settlement> findByTripIdAndPayeeId(Long tripId,Long payeeId);
}
