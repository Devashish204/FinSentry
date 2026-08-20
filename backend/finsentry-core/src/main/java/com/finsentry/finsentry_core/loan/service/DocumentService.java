package com.finsentry.finsentry_core.loan.service;

import com.finsentry.finsentry_core.exception.DocumentNotFoundException;
import com.finsentry.finsentry_core.exception.InvalidDocumentVerificationTransitionException;
import com.finsentry.finsentry_core.exception.LoanApplicationNotFoundException;
import com.finsentry.finsentry_core.loan.dto.DocumentResponse;
import com.finsentry.finsentry_core.loan.entity.Document;
import com.finsentry.finsentry_core.loan.entity.DocumentType;
import com.finsentry.finsentry_core.loan.entity.DocumentVerificationStatus;
import com.finsentry.finsentry_core.loan.entity.LoanApplication;
import com.finsentry.finsentry_core.loan.repository.DocumentRepository;
import com.finsentry.finsentry_core.loan.repository.LoanApplicationRepository;
import com.finsentry.finsentry_core.storage.StorageService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final LoanApplicationRepository loanApplicationRepository;
    private final StorageService storageService;

    public DocumentService(DocumentRepository documentRepository, LoanApplicationRepository loanApplicationRepository, StorageService storageService) {
        this.documentRepository = documentRepository;
        this.loanApplicationRepository = loanApplicationRepository;
        this.storageService = storageService;
    }

    public DocumentResponse createDocument(
            Long loanApplicationId,
            DocumentType type,
            MultipartFile file) throws IOException {

        LoanApplication loanApplication =
                loanApplicationRepository.findById(loanApplicationId)
                        .orElseThrow(() ->
                                new LoanApplicationNotFoundException(
                                        loanApplicationId
                                ));

        String storageRef = storageService.store(file);

        Document document = new Document();

        document.setLoanApplication(loanApplication);
        document.setType(type);
        document.setStorageRef(storageRef);
        document.setVerificationStatus(
                DocumentVerificationStatus.PENDING
        );
        document.setUploadedAt(OffsetDateTime.now());

        Document savedDocument = documentRepository.save(document);

        return mapToResponse(savedDocument);
    }

    public Resource getDocument(Long id) {

        Document document = documentRepository.findById(id)
                .orElseThrow(() ->
                        new DocumentNotFoundException(id));

        Path filePath = Paths.get("uploads")
                .resolve(document.getStorageRef())
                .normalize();

        Resource resource = new FileSystemResource(filePath);

        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("Document file not found");
        }

        return resource;
    }

    public DocumentResponse updateVerificationStatus(Long id, DocumentVerificationStatus status) {

        Document document = documentRepository.findById(id)
                .orElseThrow(()-> new DocumentNotFoundException(id));

       DocumentVerificationStatus currentStatus = document.getVerificationStatus();

       if(currentStatus != DocumentVerificationStatus.PENDING) {
           throw new InvalidDocumentVerificationTransitionException(
                   currentStatus.name(),
                   status.name()
           );
       }
       document.setVerificationStatus(status);

        Document updatedDocument = documentRepository.save(document);

        return mapToResponse(updatedDocument);
    }

    public DocumentResponse mapToResponse(Document document) {

        DocumentResponse documentResponse = new DocumentResponse();

        documentResponse.setId(document.getId());
        documentResponse.setLoanApplicationId(document.getLoanApplication().getId());
        documentResponse.setType(document.getType());
        documentResponse.setDocumentVerificationStatus(document.getVerificationStatus());
        documentResponse.setUploadedAt(document.getUploadedAt());

        return documentResponse;
    }
}
