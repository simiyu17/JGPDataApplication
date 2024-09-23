package com.jgp.finance.dto;

import com.jgp.finance.domain.Loan;

public record LoanSearchCriteria(
        Long partnerId,
        Long participantId,
        Loan.LoanStatus status,
        Loan.LoanQuality quality
) {
}
