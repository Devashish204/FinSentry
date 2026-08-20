package com.finsentry.finsentry_core.loan.dto;

import com.finsentry.finsentry_core.loan.entity.DocumentType;
import com.finsentry.finsentry_core.loan.entity.DocumentVerificationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class DocumentResponse {

    private Long id;
    private Long loanApplicationId;
    private DocumentType type;
    private DocumentVerificationStatus documentVerificationStatus;
    private OffsetDateTime uploadedAt;
}
