package com.finsentry.finsentry_core.exception;

public class DocumentNotFoundException extends RuntimeException {
    public DocumentNotFoundException(Long id) {
        super("Document not found with id: "+id);
    }
}
