package com.finsentry.finsentry_core.exception;

public class IncompleteDocumentsException extends RuntimeException {
    public IncompleteDocumentsException() {
        super("Cannot move loan application to PRE_SCREENING because documents are not complete");
    }
}
