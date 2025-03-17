package com.pankaj.UdhaarManagementSystem.Service;

import com.pankaj.UdhaarManagementSystem.DTO.PaymentDTO;
import com.pankaj.UdhaarManagementSystem.Entity.Customer;
import com.pankaj.UdhaarManagementSystem.Entity.Payments;
import com.pankaj.UdhaarManagementSystem.Exception.ResourceNotFoundException;
import com.pankaj.UdhaarManagementSystem.Repo.CustomerRepo;
import com.pankaj.UdhaarManagementSystem.Repo.PaymentRepo;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PaymentService {


    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<PaymentDTO> getAllPayments() {
        log.info("Fetching all payments");

        List<Payments> paymentsList = paymentRepo.findAll();
        List<PaymentDTO> paymentDTOList = new ArrayList<>();

        for (Payments payment : paymentsList) {
            log.info("Mapping payment with ID: {}", payment.getId());
            PaymentDTO paymentDTO = modelMapper.map(payment, PaymentDTO.class);
            paymentDTOList.add(paymentDTO);
        }

        log.info("Total payments fetched: {}", paymentDTOList.size());
        return paymentDTOList;

    }

    public List<PaymentDTO> getPaymentsGreaterThan(Double amount) {
        log.info("Fetching payments with amount greater than: {}", amount);

        List<Payments> payments = paymentRepo.findByAmountGreaterThan(amount);
        List<PaymentDTO> paymentDTOs = new ArrayList<>();

        for (Payments payment : payments) {
            log.info("Mapping payment with ID: {} and amount: {}", payment.getId(), payment.getAmount());
            PaymentDTO paymentDTO = modelMapper.map(payment, PaymentDTO.class);
            paymentDTOs.add(paymentDTO);
        }

        log.info("Total payments found with amount greater than {}: {}", amount, paymentDTOs.size());
        return paymentDTOs;
//        here return paymentdtos arraylist
    }

    public List<PaymentDTO> getPaymentsByCustomerId(Long customerId) {
        log.info("Fetching payments for customer ID: {}", customerId);

        List<Payments> payments = paymentRepo.findByCustomerId(customerId);
        List<PaymentDTO> paymentDTOs = new ArrayList<>();

        for (Payments payment : payments) {
            log.info("Mapping payment with ID: {}", payment.getId());
            PaymentDTO dto = modelMapper.map(payment, PaymentDTO.class);
            paymentDTOs.add(dto);
        }

        log.info("Total payments fetched for customer ID {}: {}", customerId, paymentDTOs.size());
        return paymentDTOs;
    }


    public Page<PaymentDTO> getPaymentsByCustomer(String customerName, int page, int size, String sortBy) {
        log.info("Fetching payments for customer name '{}' with pagination - Page: {}, Size: {}, SortBy: {}", customerName, page, size, sortBy);

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Payments> paymentPage = paymentRepo.findByCustomer_NameContainingIgnoreCase(customerName, pageable);

        log.info("Total payments fetched in current page: {}", paymentPage.getNumberOfElements());
        return paymentPage.map(payment -> modelMapper.map(payment, PaymentDTO.class));
    }


    public Optional<PaymentDTO> getPaymentById(Long id) {

        log.info("Fetching payment with ID: {}", id);

        Optional<Payments> payment = paymentRepo.findById(id);

        if (payment.isPresent()) {
            log.info("Payment found with ID: {}", id);
            PaymentDTO paymentDTO = modelMapper.map(payment.get(), PaymentDTO.class);
            return Optional.of(paymentDTO);
        } else {
            log.warn("Payment not found with ID: {}", id);
            return Optional.empty();
        }
    }

    public PaymentDTO addPayment(PaymentDTO paymentDTO) {
        log.info("Attempting to add new payment with details: {}", paymentDTO);
        Customer customer = customerRepo.findById(paymentDTO.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found"));

        Payments payment = modelMapper.map(paymentDTO, Payments.class);
        customer.setTotalAmountGiven(customer.getTotalAmountGiven() + paymentDTO.getAmount());
        customer.setTotalAmountReturned(customer.getTotalAmountReturned() + paymentDTO.getPaidamount());
        customerRepo.save(customer);
        Payments savedPayment = paymentRepo.save(payment);

        log.info("Payment added successfully with ID: {}", savedPayment.getId());
        return modelMapper.map(savedPayment, PaymentDTO.class);


    }

    public void deletePayment(Long id) {

//            Customer customer=customerRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("user not found with this id "));

        log.info("Attempting to delete payment with ID: {}", id);

        if (!paymentRepo.existsById(id)) {
            log.error("Payment not found with ID: {}", id);
            throw new ResourceNotFoundException("Payment not found with this id " + id);
        }

        paymentRepo.deleteById(id);
        log.info("Payment deleted successfully with ID: {}", id);
    }


}






