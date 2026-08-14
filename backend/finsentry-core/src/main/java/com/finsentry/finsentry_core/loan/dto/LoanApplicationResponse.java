package com.finsentry.finsentry_core.loan.dto;

import com.finsentry.finsentry_core.loan.entity.LoanProduct;
import com.finsentry.finsentry_core.loan.entity.LoanStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class LoanApplicationResponse {
    private Long id;
    private Long customerId;
    private LoanProduct loanProduct;
    private BigDecimal loanAmount;
    private Integer tenureMonths;
    private String purpose;
    private LoanStatus status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
