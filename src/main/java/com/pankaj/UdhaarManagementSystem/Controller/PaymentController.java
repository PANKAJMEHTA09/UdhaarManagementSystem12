package com.pankaj.UdhaarManagementSystem.Controller;


import com.pankaj.UdhaarManagementSystem.DTO.PaymentDTO;
import com.pankaj.UdhaarManagementSystem.Entity.Payments;
import com.pankaj.UdhaarManagementSystem.Service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/Payment")
public class PaymentController {





    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public PaymentDTO addPayment( @Valid @RequestBody PaymentDTO paymentsDTO){
        return  paymentService.addPayment(paymentsDTO);

    }
    @GetMapping
    public List<PaymentDTO>getallpayments(){
        return  paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public Optional<PaymentDTO> getmappingbyid(@PathVariable Long id){
        return paymentService.getPaymentById(id);
    }

    @DeleteMapping("/{id}")
    public void DeleteMapping(@PathVariable Long id){
        paymentService.deletePayment(id);
    }





}
