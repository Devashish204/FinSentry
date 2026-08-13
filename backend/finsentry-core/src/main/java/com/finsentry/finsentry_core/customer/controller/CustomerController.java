package com.finsentry.finsentry_core.customer.controller;

import com.finsentry.finsentry_core.customer.dto.CreateCustomerRequest;
import com.finsentry.finsentry_core.customer.dto.CustomerResponse;
import com.finsentry.finsentry_core.customer.dto.CustomerUpdateRequest;
import com.finsentry.finsentry_core.customer.entity.Customer;
import com.finsentry.finsentry_core.customer.repository.CustomerRepository;
import com.finsentry.finsentry_core.customer.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public CustomerResponse createCustomer(
            @Valid @RequestBody CreateCustomerRequest createCustomerRequest) {

        return customerService.createCustomer(createCustomerRequest);
    }

    @GetMapping("/{id}")
    public CustomerResponse getCustomer(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping
    public List<CustomerResponse> getAllCustomers(@RequestParam int page, @RequestParam int size) {
        return customerService.getAllCustomers(page,size);
    }

    @PutMapping("/{id}")
    public CustomerResponse updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerUpdateRequest customerUpdateRequest) {

        return customerService.updateCustomer(id, customerUpdateRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
