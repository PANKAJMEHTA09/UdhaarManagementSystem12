package com.pankaj.UdhaarManagementSystem.Controller;


import com.pankaj.UdhaarManagementSystem.DTO.CustomerDTO;
import com.pankaj.UdhaarManagementSystem.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customer")
@Slf4j
public class CustomerController {

    @Autowired

    private CustomerService customerService;

    @PostMapping
    public CustomerDTO AddCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        log.info("Received request to add customer: {}", customerDTO);
        CustomerDTO savedCustomer = customerService.AddCustomer(customerDTO);
        log.info("Customer added successfully with ID: {}", savedCustomer.getId());
        return savedCustomer;
    }

    @GetMapping
    public Page<CustomerDTO> getAllCustomers(


            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "0") String name,

            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        log.info("Fetching customers with parameters - Page: {}, Name: '{}', Size: {}, SortBy: '{}'", page, name, size, sortBy);
        Page<CustomerDTO> customers = customerService.getCustomers(name, page, size, sortBy);

        log.info("Fetching customers with parameters - Page: {}, Name: '{}', Size: {}, SortBy: '{}'", page, name, size, sortBy);
        return customers;
    }

//
//@GetMapping
//    public List<CustomerDTO> getCustomer(){
//    return  customerService.findallCustomer();
//}

    @GetMapping("/{id}")
    public CustomerDTO getCustomerbyid(@PathVariable Long id) {
        log.info("Fetching customer with ID: {}", id);
        CustomerDTO customerDTO = customerService.GetCustomerById(id);
        log.info("Fetched customer: {}", customerDTO);
        return customerDTO;
    }
//
//    @GetMapping("/user/{userId}")
//    public List<CustomerDTO> getCustomersByUserId(@PathVariable Long userId) {
//        return customerService.getCustomersByUserId(userId);
//    }
//
//    @GetMapping("/name/{name}")
//    public List<CustomerDTO> getCustomersByName(@PathVariable String name) {
//        return customerService.getCustomersByName(name);
//    }


    @DeleteMapping("/{id}")
    public void DeleteCustomerbyId(@PathVariable Long id) {
        log.info("Request received to delete customer with ID: {}", id);
        customerService.DeleteCustomer(id);
        log.info("Customer deleted successfully with ID: {}", id);
    }


    @PostMapping("/{id}/add-udhaar")
    public CustomerDTO addUdhaar(@PathVariable("id") Long customerId,
                                 @RequestParam("amount") double amount) {
        log.info("Adding udhaar of amount {} for customer ID: {}", amount, customerId);
        CustomerDTO updatedCustomer = customerService.updateTotalAmountGiven(customerId, amount);
        log.info("Updated customer after adding udhaar: {}", updatedCustomer);
        return updatedCustomer;
    }


    @PostMapping("/{id}/repay-udhaar")
    public CustomerDTO repayUdhaar(@PathVariable("id") Long customerId,
                                   @RequestParam("amount") double amount) {
        log.info("Repaying udhaar of amount {} for customer ID: {}", amount, customerId);
        CustomerDTO updatedCustomer = customerService.updateTotalAmountReturned(customerId, amount);
        log.info("Updated customer after repayment: {}", updatedCustomer);
        return updatedCustomer;


    }


    @GetMapping("/customers/{customerId}/remaining-udhaar")
    public double getRemainingUdhaar(@PathVariable Long customerId) {
        log.info("Calculating remaining udhaar for customer ID: {}", customerId);
        CustomerDTO customerDTO = customerService.GetCustomerById(customerId);

        double remainingUdhaar = customerDTO.getTotalAmountGiven() - customerDTO.getTotalAmountReturned();
        log.info("Remaining udhaar for customer ID {}: {}", customerId, remainingUdhaar);
        return remainingUdhaar;
    }


}
