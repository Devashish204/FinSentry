package com.finsentry.finsentry_core.loan.repository;

import com.finsentry.finsentry_core.loan.entity.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication,Long>{

}
