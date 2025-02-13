package com.pankaj.UdhaarManagementSystem.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class PaymentDTO {

    private Long id;
    @Min(value = 1, message = "Amount must be at least 1")
    private double amount;
    @Min(value = 0, message = "Paid amount cannot be negative")
    private double Paidamount;

    private String description;
    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;


}
