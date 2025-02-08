package com.pankaj.UdhaarManagementSystem.Controller;


import com.pankaj.UdhaarManagementSystem.Entity.Payments;
import com.pankaj.UdhaarManagementSystem.Service.PaymentService;
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
    public Payments addPayment(@RequestBody Payments payments){
        return  paymentService.addPayment(payments);

    }
    @GetMapping
    public List<Payments>getallpayments(){
        return  paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public Optional<Payments> getmappingbyid(@PathVariable Long id){
        return paymentService.getPaymentById(id);
    }

    @DeleteMapping("/{id}")
    public void DeleteMapping(@PathVariable Long id){
        paymentService.deletePayment(id);
    }





}
