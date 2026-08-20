package com.finsentry.finsentry_core.loan.controller;

import com.finsentry.finsentry_core.loan.dto.DocumentResponse;
import com.finsentry.finsentry_core.loan.dto.UpdateDocumentVerificationRequest;
import com.finsentry.finsentry_core.loan.entity.DocumentType;
import com.finsentry.finsentry_core.loan.service.DocumentService;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DocumentResponse createDocument(
            @RequestParam Long loanApplicationId,
            @RequestParam DocumentType type,
            @RequestParam MultipartFile file) throws IOException {

        return documentService.createDocument(
                loanApplicationId,
                type,
                file
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getDocument(@PathVariable Long id){

        Resource resource = documentService.getDocument(id);

        MediaType mediaType = MediaTypeFactory
                .getMediaType(resource)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(resource);
    }

    @PatchMapping("/{id}/verification")
    public DocumentResponse updateVerification(@PathVariable Long id, @Valid @RequestBody UpdateDocumentVerificationRequest request){
        return documentService.updateVerificationStatus(
                id, request.getStatus()
        );
    }
}
