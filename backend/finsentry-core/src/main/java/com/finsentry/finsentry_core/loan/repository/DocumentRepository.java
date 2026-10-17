package com.finsentry.finsentry_core.loan.repository;

import com.finsentry.finsentry_core.loan.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByLoanApplicationId(Long loanApplicationId);
}
