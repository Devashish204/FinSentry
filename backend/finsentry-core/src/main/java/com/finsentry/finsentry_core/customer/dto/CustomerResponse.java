package com.finsentry.finsentry_core.customer.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
public class CustomerResponse {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
