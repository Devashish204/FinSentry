package com.finsentry.finsentry_core.loan.service;

import com.finsentry.finsentry_core.customer.entity.Customer;
import com.finsentry.finsentry_core.customer.repository.CustomerRepository;
import com.finsentry.finsentry_core.exception.CustomerNotFoundException;
import com.finsentry.finsentry_core.exception.InvalidLoanStatusTransitionException;
import com.finsentry.finsentry_core.exception.LoanApplicationNotFoundException;
import com.finsentry.finsentry_core.loan.dto.CreateLoanApplicationRequest;
import com.finsentry.finsentry_core.loan.dto.LoanApplicationResponse;
import com.finsentry.finsentry_core.loan.entity.LoanApplication;
import com.finsentry.finsentry_core.loan.entity.LoanStatus;
import com.finsentry.finsentry_core.loan.repository.LoanApplicationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final CustomerRepository customerRepository;

    public LoanApplicationService(LoanApplicationRepository loanApplicationRepository, CustomerRepository customerRepository) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.customerRepository = customerRepository;
    }

    public LoanApplicationResponse createLoanApplication(CreateLoanApplicationRequest createLoanApplicationRequest) {

        Customer customer = customerRepository.findById(createLoanApplicationRequest.getCustomerId())
                .orElseThrow(()-> new CustomerNotFoundException(createLoanApplicationRequest.getCustomerId()));

        LoanApplication loanApplication = new LoanApplication();

        loanApplication.setCustomer(customer);
        loanApplication.setLoanProduct(createLoanApplicationRequest.getLoanProduct());
        loanApplication.setLoanAmount(createLoanApplicationRequest.getLoanAmount());
        loanApplication.setTenureMonths(createLoanApplicationRequest.getTenureMonths());
        loanApplication.setPurpose(createLoanApplicationRequest.getPurpose());
        loanApplication.setStatus(LoanStatus.CREATED);

        OffsetDateTime now = OffsetDateTime.now();
        loanApplication.setCreatedAt(now);
        loanApplication.setUpdatedAt(now);

        LoanApplication savedLoanApplication = loanApplicationRepository.save(loanApplication);

        return mapToResponse(savedLoanApplication);
    }

    public LoanApplicationResponse getLoanApplicationById(Long id){
        LoanApplication loanApplication = loanApplicationRepository.findById(id)
                .orElseThrow(()-> new LoanApplicationNotFoundException(id));

        return mapToResponse(loanApplication);
    }

    private LoanApplicationResponse mapToResponse(LoanApplication loanApplication) {
        LoanApplicationResponse response = new LoanApplicationResponse();

        response.setId(loanApplication.getId());
        response.setCustomerId(loanApplication.getCustomer().getId());
        response.setLoanProduct(loanApplication.getLoanProduct());
        response.setLoanAmount(loanApplication.getLoanAmount());
        response.setTenureMonths(loanApplication.getTenureMonths());
        response.setPurpose(loanApplication.getPurpose());
        response.setStatus(loanApplication.getStatus());
        response.setCreatedAt(loanApplication.getCreatedAt());
        response.setUpdatedAt(loanApplication.getUpdatedAt());
        return response;
    }

    public List<LoanApplicationResponse> getAllLoanApplications(int page, int size){

        Pageable pageable = PageRequest.of(page, size);


        Page<LoanApplication> applications = loanApplicationRepository.findAll(pageable);

        return applications.getContent()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private boolean isValidTransition(
            LoanStatus currentStatus,
            LoanStatus newStatus) {

        return switch (currentStatus) {

            case CREATED ->
                    newStatus == LoanStatus.DOCS_PENDING
                            || newStatus == LoanStatus.WITHDRAWN;

            case DOCS_PENDING ->
                    newStatus == LoanStatus.PRE_SCREENING
                            || newStatus == LoanStatus.INFO_REQUESTED
                            || newStatus == LoanStatus.WITHDRAWN;

            case PRE_SCREENING ->
                    newStatus == LoanStatus.RISK_ASSESSMENT
                            || newStatus == LoanStatus.REJECTED
                            || newStatus == LoanStatus.INFO_REQUESTED
                            || newStatus == LoanStatus.WITHDRAWN;

            case RISK_ASSESSMENT ->
                    newStatus == LoanStatus.UNDERWRITING
                            || newStatus == LoanStatus.REJECTED
                            || newStatus == LoanStatus.INFO_REQUESTED
                            || newStatus == LoanStatus.WITHDRAWN;

            case UNDERWRITING ->
                    newStatus == LoanStatus.APPROVED
                            || newStatus == LoanStatus.REJECTED
                            || newStatus == LoanStatus.INFO_REQUESTED
                            || newStatus == LoanStatus.WITHDRAWN;

            case APPROVED ->
                    newStatus == LoanStatus.SANCTIONED
                            || newStatus == LoanStatus.WITHDRAWN;

            case SANCTIONED ->
                    newStatus == LoanStatus.VERIFIED
                            || newStatus == LoanStatus.WITHDRAWN;

            case VERIFIED ->
                    newStatus == LoanStatus.DISBURSED;

            case DISBURSED ->
                    newStatus == LoanStatus.REPAYING;

            case REPAYING ->
                    newStatus == LoanStatus.CLOSED
                            || newStatus == LoanStatus.DEFAULTED;

            case INFO_REQUESTED ->
                    newStatus == LoanStatus.PRE_SCREENING
                            || newStatus == LoanStatus.RISK_ASSESSMENT
                            || newStatus == LoanStatus.UNDERWRITING
                            || newStatus == LoanStatus.WITHDRAWN;

            case CLOSED, REJECTED, WITHDRAWN, DEFAULTED ->
                    false;
        };
    }

    public LoanApplicationResponse updateLoanStatus(Long id, LoanStatus newStatus){

        LoanApplication loanApplication = loanApplicationRepository.findById(id)
                .orElseThrow(()->new LoanApplicationNotFoundException(id));

        LoanStatus currentStatus = loanApplication.getStatus();

        if(!isValidTransition(currentStatus, newStatus)){
            throw new InvalidLoanStatusTransitionException(
                    currentStatus.name(),
                    newStatus.name()
            );
        }

        loanApplication.setStatus(newStatus);
        loanApplication.setUpdatedAt(OffsetDateTime.now());

        LoanApplication updatedLoanApplication = loanApplicationRepository.save(loanApplication);
        return mapToResponse(updatedLoanApplication);
    }
}
