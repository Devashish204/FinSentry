package com.finsentry.finsentry_core.loan.dto;

import com.finsentry.finsentry_core.loan.entity.LoanProduct;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateLoanApplicationRequest {

    @NotNull
    private Long customerId;

    @NotNull
    private LoanProduct loanProduct;

    @NotNull
    @DecimalMin(value = "1.00")
    private BigDecimal loanAmount;

    @NotNull
    @Positive
    private Integer tenureMonths;

    @NotBlank
    private String purpose;
}
