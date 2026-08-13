package com.finsentry.finsentry_core.customer.service;

import com.finsentry.finsentry_core.customer.dto.CreateCustomerRequest;
import com.finsentry.finsentry_core.customer.dto.CustomerResponse;
import com.finsentry.finsentry_core.customer.dto.CustomerUpdateRequest;
import com.finsentry.finsentry_core.customer.entity.Customer;
import com.finsentry.finsentry_core.customer.repository.CustomerRepository;
import com.finsentry.finsentry_core.exception.CustomerNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest) {
        Customer customer = new Customer();
        customer.setFirstName(createCustomerRequest.getFirstName());
        customer.setMiddleName(createCustomerRequest.getMiddleName());
        customer.setLastName(createCustomerRequest.getLastName());
        customer.setEmail(createCustomerRequest.getEmail());
        customer.setPhone(createCustomerRequest.getPhone());
        customer.setDateOfBirth(createCustomerRequest.getDateOfBirth());

        OffsetDateTime now = OffsetDateTime.now();
        customer.setCreatedAt(now);
        customer.setUpdatedAt(now);
        Customer savedCustomer = customerRepository.save(customer);

        return mapToResponse(savedCustomer);
    }

    public CustomerResponse getCustomerById(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        return mapToResponse(customer);
    }

    public List<CustomerResponse> getAllCustomers(int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        Page<Customer> customers = customerRepository.findAll(pageable);

        return customers.getContent()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public CustomerResponse updateCustomer(
            Long id,
            CustomerUpdateRequest updateCustomerRequest) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        customer.setFirstName(updateCustomerRequest.getFirstName());
        customer.setMiddleName(updateCustomerRequest.getMiddleName());
        customer.setLastName(updateCustomerRequest.getLastName());
        customer.setEmail(updateCustomerRequest.getEmail());
        customer.setPhone(updateCustomerRequest.getPhone());
        customer.setDateOfBirth(updateCustomerRequest.getDateOfBirth());

        customer.setUpdatedAt(OffsetDateTime.now());

        Customer updatedCustomer = customerRepository.save(customer);

        return mapToResponse(updatedCustomer);
    }

    public void deleteCustomer(Long id){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        customerRepository.deleteById(customer.getId());
    }

    private CustomerResponse mapToResponse(Customer customer) {

        CustomerResponse response = new CustomerResponse();

        response.setId(customer.getId());
        response.setFirstName(customer.getFirstName());
        response.setMiddleName(customer.getMiddleName());
        response.setLastName(customer.getLastName());
        response.setEmail(customer.getEmail());
        response.setPhone(customer.getPhone());
        response.setDateOfBirth(customer.getDateOfBirth());
        response.setCreatedAt(customer.getCreatedAt());
        response.setUpdatedAt(customer.getUpdatedAt());

        return response;
    }
}
