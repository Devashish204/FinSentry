package com.finsentry.finsentry_core.exception;

public class InvalidDocumentVerificationTransitionException extends RuntimeException {
    public InvalidDocumentVerificationTransitionException(
            String currentStatus,
            String requestedStatus) {

        super("Invalid document verification status transition from "
                + currentStatus + " to " + requestedStatus);
    }}
