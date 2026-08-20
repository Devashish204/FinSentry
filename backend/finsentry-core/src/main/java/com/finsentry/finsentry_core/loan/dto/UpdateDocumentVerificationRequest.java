package com.finsentry.finsentry_core.loan.dto;

import com.finsentry.finsentry_core.loan.entity.DocumentVerificationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDocumentVerificationRequest {

    @NotNull
    private DocumentVerificationStatus status;

}
