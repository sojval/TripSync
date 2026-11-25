package com.example.tripsync.expenses.Dtos;

import java.math.BigDecimal;

public record MemberBalanceResponse(
        Long userId,
        String fullName,
        BigDecimal totalPaid,
        BigDecimal totalShare,
        BigDecimal settlementSent,
        BigDecimal settlementReceived,
        BigDecimal netBalance
) {
}
