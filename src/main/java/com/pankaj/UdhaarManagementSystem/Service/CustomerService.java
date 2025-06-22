package com.pankaj.UdhaarManagementSystem.Service;

import com.pankaj.UdhaarManagementSystem.DTO.CustomerDTO;
import com.pankaj.UdhaarManagementSystem.Entity.Customer;
import com.pankaj.UdhaarManagementSystem.Exception.ResourceAlreadyExistsException;
import com.pankaj.UdhaarManagementSystem.Exception.ResourceNotFoundException;
import com.pankaj.UdhaarManagementSystem.Repo.CustomerRepo;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;


    @Autowired
    private ModelMapper modelMapper;

    public CustomerDTO AddCustomer(CustomerDTO customerDTO) {

        log.info("Attempting to add a new customer with name: {}", customerDTO.getName());

        if (!customerRepo.findByName(customerDTO.getName()).isEmpty()) {
            log.warn("Customer with name '{}' already exists", customerDTO.getName());
            throw new ResourceAlreadyExistsException("Customer with this name already exists");
        }

        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customer.setTotalAmountOfDebt(0.0);
        customer = customerRepo.save(customer);
        log.info("Customer successfully added with ID: {}", customer.getId());

        return modelMapper.map(customer, CustomerDTO.class);

    }

    public void DeleteCustomer(Long id) {
        log.info("Attempting to delete customer with ID: {}", id);

        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Customer not found with ID: {}", id);
                    return new ResourceNotFoundException("Customer not found with this id");
                });

        customerRepo.deleteById(id);
        log.info("Customer with ID: {} successfully deleted", id);
    }

    public CustomerDTO GetCustomerById(Long id) {
        log.info("Fetching customer details for ID: {}", id);

        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Customer not found with ID: {}", id);
                    return new ResourceNotFoundException("Customer not found with this id");
                });

        log.info("Customer details retrieved for ID: {}", id);
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public List<CustomerDTO> getCustomersByUserId(Long userId) {
        log.info("Fetching customers for User ID: {}", userId);

        List<Customer> customers = customerRepo.findByUserId(userId);
        List<CustomerDTO> customerDTOs = new ArrayList<>();

        if (customers.isEmpty()) {
            log.warn("No customers found for User ID: {}", userId);
        }

        for (Customer customer : customers) {
            log.info("Mapping Customer ID: {} with name: {}", customer.getId(), customer.getName());
            CustomerDTO dto = modelMapper.map(customer, CustomerDTO.class);
            customerDTOs.add(dto);
        }

        log.info("Total customers fetched for User ID {}: {}", userId, customerDTOs.size());
        return customerDTOs;
    }

    public List<CustomerDTO> getCustomersByName(String name) {
        log.info("Fetching customers with name: {}", name);

        List<Customer> customers = customerRepo.findByName(name);
        List<CustomerDTO> customerDTOs = new ArrayList<>();

        if (customers.isEmpty()) {
            log.warn("No customers found with name: {}", name);
        }

        for (Customer customer : customers) {
            log.info("Mapping Customer ID: {} with name: {}", customer.getId(), customer.getName());
            CustomerDTO dto = modelMapper.map(customer, CustomerDTO.class);
            customerDTOs.add(dto);
        }

        log.info("Total customers fetched with name '{}': {}", name, customerDTOs.size());
        return customerDTOs;
    }


    public Page<CustomerDTO> getCustomers(String name, int page, int size, String sortBy) {
        log.info("Fetching customers - Page: {}, Size: {}, SortBy: {}", page, size, sortBy);

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Customer> customerPage = customerRepo.findAll(pageable);

        return customerPage.map(customer -> {
            log.info("Customer on page: {}", customer.getName());
            return modelMapper.map(customer, CustomerDTO.class);
        });
    }


    public List<CustomerDTO> findAllCustomer() {
        log.info("Fetching all customers");

        List<CustomerDTO> customerDTOList = new ArrayList<>();
        for (Customer customer : customerRepo.findAll()) {
            log.info("Mapping Customer ID: {} with name: {}", customer.getId(), customer.getName());
            customerDTOList.add(modelMapper.map(customer, CustomerDTO.class));
        }

        log.info("Total customers found: {}", customerDTOList.size());
        return customerDTOList;
    }


//    public CustomerDTO updateTotalAmountGiven(Long customerId, double amountGiven) {
//        log.info("Updating total amount given for Customer ID: {}", customerId);
//
//        Customer customer = customerRepo.findById(customerId)
//                .orElseThrow(() -> {
//                    log.error("Customer not found with ID: {}", customerId);
//                    return new ResourceNotFoundException("Customer not found with id: " + customerId);
//                });
//
//        customer.setTotalAmountGiven(customer.getTotalAmountGiven() + amountGiven);
//        customer = customerRepo.save(customer);
//
//        log.info("Total amount given updated for Customer ID: {}. New Amount: {}", customerId, customer.getTotalAmountGiven());
//        return modelMapper.map(customer, CustomerDTO.class);
//    }

//    public CustomerDTO updateTotalAmountReturned(Long customerId, double amountReturned) {
//        log.info("Updating total amount returned for Customer ID: {}", customerId);
//
//        Customer customer = customerRepo.findById(customerId)
//                .orElseThrow(() -> {
//                    log.error("Customer not found with ID: {}", customerId);
//                    return new ResourceNotFoundException("Customer not found with id: " + customerId);
//                });
//
//        customer.setTotalAmountReturned(customer.getTotalAmountReturned() + amountReturned);
//        customer = customerRepo.save(customer);
//
//        log.info("Total amount returned updated for Customer ID: {}. New Amount: {}", customerId, customer.getTotalAmountReturned());
//        return modelMapper.map(customer, CustomerDTO.class);
//    }


// scheduler methodd


    @Scheduled(cron = "0 * * * * ?")
    public void logDailyCustomerPayments() {
        List<Customer> customers = customerRepo.findAll();

        if (customers.isEmpty()) {
            log.info("No customer payment data available for today.");
        } else {
            for (Customer customer : customers) {
                log.info("Customer Name: {}, Phone No: {}, Total Amount Given: {}",
                        customer.getName(),
                        customer.getPhone_no(),
                        customer.getTotalAmountOfDebt());
                customer.getTotalAmountReturnedDebt();
            }
        }
    }


}
