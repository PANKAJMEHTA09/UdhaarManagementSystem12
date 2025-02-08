package com.pankaj.UdhaarManagementSystem.Service;

import com.pankaj.UdhaarManagementSystem.Entity.Payments;
import com.pankaj.UdhaarManagementSystem.Repo.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {


    @Autowired
    private PaymentRepo paymentRepo;

        public List<Payments> getAllPayments() {
            return paymentRepo.findAll();
        }

        public Optional<Payments> getPaymentById(Long id) {
            return paymentRepo.findById(id);
        }

        public Payments addPayment(Payments payment) {
            return paymentRepo.save(payment);
        }

        public void deletePayment(Long id) {
            paymentRepo.deleteById(id);
        }
    }






