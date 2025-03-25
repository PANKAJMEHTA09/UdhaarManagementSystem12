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
import org.springframework.http.converter.json.GsonBuilderUtils;
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

    public Page<PaymentDTO> getAllPayments(int page, int size, String sortBy) {
        log.info("Fetching all payments");

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy));
        Page<Payments> paymentsPage = paymentRepo.findAll(pageable);
        return paymentsPage.map(payments -> modelMapper.map(payments, PaymentDTO.class));
    }

//        List<Payments> paymentsList = paymentRepo.findAll();
//        List<PaymentDTO> paymentDTOList = new ArrayList<>();
//
//        for (Payments payment : paymentsList) {
//            log.info("Mapping payment with ID: {}", payment.getId());
//            PaymentDTO paymentDTO = modelMapper.map(payment, PaymentDTO.class);
//            paymentDTOList.add(paymentDTO);
//        }
//
//        log.info("Total payments fetched: {}", paymentDTOList.size());
//        return paymentDTOList;

//        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy));
//        Page<Payments>paymentsPage = paymentRepo.findAll(pageable);
//
//   return paymentPage.map(payment -> modelMapper.map(payment, PaymentDTO.class));
//        return paymentsPage.map(payments -> modelMapper.map(payments, PaymentDTO.class));
//
//    }






//        public Page<PaymentDTO> getPaymentsByCustomer(String customerName, int page, int size, String sortBy) {
//        log.info("Fetching payments for customer name '{}' with pagination - Page: {}, Size: {}, SortBy: {}", customerName, page, size, sortBy);
//
//        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
//        Page<Payments> paymentPage = paymentRepo.findByCustomer_NameContainingIgnoreCase(customerName, pageable);
//
//        log.info("Total payments fetched in current page: {}", paymentPage.getNumberOfElements());
//        return paymentPage.map(payment -> modelMapper.map(payment, PaymentDTO.class));
//    }


// for my updating payment


public PaymentDTO updatePayment(Long id ,PaymentDTO paymentDTO){
   log.info("Updating payment with id {}",id);

//   Fetch exixts payment
   Payments existingPayment = paymentRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Payment not found"));

//   fetch associated customer
    Customer customer = customerRepo.findById(existingPayment.getCustomer().getId()).orElseThrow(()->new ResourceNotFoundException("customer not found"));

//    revert old payments amount from customer's record

    customer.setTotalAmountofDebt(customer.getTotalAmountofDebt()-existingPayment.getAmount());
    customer.setTotalAmountReturnedDebt(customer.getTotalAmountReturnedDebt()- existingPayment.getPaidamount());

//update payment details with new values
    existingPayment.setAmount(paymentDTO.getAmount());
    existingPayment.setPaidamount(paymentDTO.getPaidamount());
    existingPayment.setPaymentDate(paymentDTO.getPaymentDate());

    customerRepo.save(customer);
    Payments updatedPayment  = paymentRepo.save(existingPayment);

    return modelMapper.map(updatedPayment, PaymentDTO.class);





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


//    public Page<PaymentDTO> getPaymentsByCustomer(String customerName, int page, int size, String sortBy) {
//        log.info("Fetching payments for customer name '{}' with pagination - Page: {}, Size: {}, SortBy: {}", customerName, page, size, sortBy);
//
//        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
//        Page<Payments> paymentPage = paymentRepo.findByCustomer_NameContainingIgnoreCase(customerName, pageable);
//
//        log.info("Total payments fetched in current page: {}", paymentPage.getNumberOfElements());
//        return paymentPage.map(payment -> modelMapper.map(payment, PaymentDTO.class));
//    }


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
        customer.setTotalAmountofDebt(customer.getTotalAmountofDebt() + paymentDTO.getAmount());
        customer.setTotalAmountReturnedDebt(customer.getTotalAmountReturnedDebt() + paymentDTO.getPaidamount());
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






