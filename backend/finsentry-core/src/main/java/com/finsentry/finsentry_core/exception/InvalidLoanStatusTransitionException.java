package com.finsentry.finsentry_core.exception;

public class InvalidLoanStatusTransitionException extends RuntimeException{
    public InvalidLoanStatusTransitionException(
            String currentStatus,
            String newStatus) {

        super("Invalid loan status transition from "
                + currentStatus + " to " + newStatus);
    }
}
