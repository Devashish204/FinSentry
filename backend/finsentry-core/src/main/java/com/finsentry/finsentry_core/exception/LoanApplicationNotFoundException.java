package com.finsentry.finsentry_core.exception;

public class LoanApplicationNotFoundException extends RuntimeException {
    public LoanApplicationNotFoundException(Long id) {
        super("Loan application id " + id + " not found");
    }
}
