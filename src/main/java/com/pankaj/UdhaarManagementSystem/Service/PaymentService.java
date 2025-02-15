package com.pankaj.UdhaarManagementSystem.Service;

import com.pankaj.UdhaarManagementSystem.DTO.PaymentDTO;
import com.pankaj.UdhaarManagementSystem.Entity.Customer;
import com.pankaj.UdhaarManagementSystem.Entity.Payments;
import com.pankaj.UdhaarManagementSystem.Exception.ResourceNotFoundException;
import com.pankaj.UdhaarManagementSystem.Repo.CustomerRepo;
import com.pankaj.UdhaarManagementSystem.Repo.PaymentRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {


    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private ModelMapper modelMapper;

        public List<PaymentDTO> getAllPayments() {
            List<Payments> paymentsList = paymentRepo.findAll();
            List<PaymentDTO> paymentDTOList = new ArrayList<>();

            for (Payments payment : paymentsList) {
                PaymentDTO paymentDTO = modelMapper.map(payment, PaymentDTO.class);
//                paymentDTO.setCustomer(payment.getCustomer().getId()); // Set customer ID manually
                paymentDTOList.add(paymentDTO);
            }

            return paymentDTOList;

        }

        public Optional<PaymentDTO> getPaymentById(Long id) {

            Optional<Payments> payment = paymentRepo.findById(id);

            if (payment.isPresent()) {
                PaymentDTO paymentDTO = modelMapper.map(payment.get(), PaymentDTO.class);
//                paymentDTO.setCustomerId(payment.get().getCustomer().getId());
                return Optional.of(paymentDTO);
            } else {
                return Optional.empty();
            }
        }

        public PaymentDTO addPayment(PaymentDTO paymentDTO) {
            Payments payment = modelMapper.map(paymentDTO, Payments.class);

            // Manually set Customer from ID
//            Optional<Customer> customer = CustomerRepo.findById(PaymentDTO.getCustomerId());
//            customer.ifPresent(payment::setCustomer);

            Payments savedPayment = paymentRepo.save(payment);
            return modelMapper.map(savedPayment, PaymentDTO.class);


        }

        public void deletePayment(Long id) {

//            Customer customer=customerRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("user not found with this id "));


            paymentRepo.deleteById(id);
        }
    }






