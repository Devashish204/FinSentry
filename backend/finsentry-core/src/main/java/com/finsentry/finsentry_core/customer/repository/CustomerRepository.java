package com.finsentry.finsentry_core.customer.repository;

import com.finsentry.finsentry_core.customer.dto.CustomerResponse;
import com.finsentry.finsentry_core.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
