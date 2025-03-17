package com.pankaj.UdhaarManagementSystem.Controller;


import com.pankaj.UdhaarManagementSystem.DTO.PaymentDTO;
import com.pankaj.UdhaarManagementSystem.Entity.Payments;
import com.pankaj.UdhaarManagementSystem.Service.PaymentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/Payment")
@Slf4j
public class PaymentController {





    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public PaymentDTO addPayment( @Valid @RequestBody PaymentDTO paymentsDTO){
        log.info("Adding payment: {}", paymentsDTO);
        PaymentDTO savedPayment = paymentService.addPayment(paymentsDTO);
        log.info("Payment added successfully with ID: {}", savedPayment.getId());
        return savedPayment;
    }
    @GetMapping("/greater-than/{amount}")
    public List<PaymentDTO> getPaymentsGreaterThan(@PathVariable Double amount) {
        log.info("Fetching payments greater than amount: {}", amount);
        List<PaymentDTO> payments = paymentService.getPaymentsGreaterThan(amount);
        log.info("Found {} payments greater than {}", payments.size(), amount);
        return payments;
    }

    @GetMapping("/customer/{customerId}")
    public List<PaymentDTO> getPaymentsByCustomerId(@PathVariable Long customerId) {
        log.info("Fetching payments for customer ID: {}", customerId);
        List<PaymentDTO> payments = paymentService.getPaymentsByCustomerId(customerId);
        log.info("Found {} payments for customer ID: {}", payments.size(), customerId);
        return payments;
    }

    @GetMapping
    public Page<PaymentDTO> getPaymentsByCustomer(
            @RequestParam(defaultValue = "") String customerName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        log.info("Fetching payments for customer '{}', Page: {}, Size: {}, SortBy: {}", customerName, page, size, sortBy);
        Page<PaymentDTO> payments = paymentService.getPaymentsByCustomer(customerName, page, size, sortBy);
        log.info("Fetched {} payments for customer '{}'", payments.getTotalElements(), customerName);
        return payments;
    }







//    @GetMapping
//    public List<PaymentDTO>getallpayments(){
//        return  paymentService.getAllPayments();
//    }

    @GetMapping("/{id}")
    public Optional<PaymentDTO> getmappingbyid(@PathVariable Long id){
        log.info("Fetching payment with ID: {}", id);
        Optional<PaymentDTO> payment = paymentService.getPaymentById(id);
        if (payment.isPresent()) {
            log.info("Payment found with ID: {}", id);
        } else {
            log.warn("No payment found with ID: {}", id);
        }
        return payment;

    }

    @DeleteMapping("/{id}")
    public void DeleteMapping(@PathVariable Long id){
        log.info("Deleting payment with ID: {}", id);
        paymentService.deletePayment(id);
        log.info("Payment deleted with ID: {}", id);
    }





}
