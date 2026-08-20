package com.finsentry.finsentry_core.loan.dto;

import com.finsentry.finsentry_core.loan.entity.DocumentType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateDocumentRequest {

    @NotNull
    private Long loanApplicationId;

    @NotNull
    private DocumentType type;
}
