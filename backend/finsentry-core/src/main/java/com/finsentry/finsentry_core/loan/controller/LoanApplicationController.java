package com.finsentry.finsentry_core.loan.controller;

import com.finsentry.finsentry_core.loan.dto.CreateLoanApplicationRequest;
import com.finsentry.finsentry_core.loan.dto.LoanApplicationResponse;
import com.finsentry.finsentry_core.loan.dto.UpdateLoanStatusRequest;
import com.finsentry.finsentry_core.loan.service.LoanApplicationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan-applications")
public class LoanApplicationController {

    private final LoanApplicationService loanApplicationService;

    public LoanApplicationController(LoanApplicationService loanApplicationService) {
        this.loanApplicationService = loanApplicationService;
    }

    @PostMapping
    public LoanApplicationResponse createLoanApplication(@Valid @RequestBody CreateLoanApplicationRequest request) {
        return loanApplicationService.createLoanApplication(request);
    }

    @GetMapping("/{id}")
    public LoanApplicationResponse getLoanApplication(@PathVariable Long id) {
        return loanApplicationService.getLoanApplicationById(id);
    }

    @GetMapping
    public List<LoanApplicationResponse> getAllLoanApplications(@RequestParam int page, @RequestParam int size){
        return loanApplicationService.getAllLoanApplications(page, size);
    }

    @PatchMapping("/{id}/status")
    public LoanApplicationResponse updateLoanApplicationStatus(@PathVariable Long id, @Valid @RequestBody UpdateLoanStatusRequest request) {
        return loanApplicationService.updateLoanStatus(id,request.getStatus());
    }
}
