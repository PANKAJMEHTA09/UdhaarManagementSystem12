package com.pankaj.UdhaarManagementSystem.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    private Long id;
    @Min(value = 1, message = "Amount must be at least 1")
    private double amount;

    @PositiveOrZero
    private double Paidamount;

    private LocalDate paymentDate;


    @Size(max = 255)
    private String description;
    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;




}
