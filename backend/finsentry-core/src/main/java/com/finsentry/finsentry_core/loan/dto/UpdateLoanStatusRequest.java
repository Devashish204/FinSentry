package com.finsentry.finsentry_core.loan.dto;

import com.finsentry.finsentry_core.loan.entity.LoanStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateLoanStatusRequest {

    @NotNull
    private LoanStatus status;
}
