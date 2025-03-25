package com.pankaj.UdhaarManagementSystem.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate paymentDate;
    private double amount;
    private double Paidamount;
    private double remainingAmount;
    private String description;


    @ManyToOne
    private Customer customer;


    @PreUpdate
    @PrePersist
    public void validateAndCalculateRemaining() {
        if (this.Paidamount > this.amount) {
            throw new IllegalStateException("Paid amount cannot exceed total amount");
        }
        this.remainingAmount = this.amount - this.Paidamount;
    }



}
