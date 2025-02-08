package com.pankaj.UdhaarManagementSystem.Controller;


import com.pankaj.UdhaarManagementSystem.Entity.Customer;
import com.pankaj.UdhaarManagementSystem.Service.CustomerService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {

@Autowired
    private CustomerService customerService;

@PostMapping
public Customer AddCustomer(@RequestBody Customer customer){
      return customerService.AddCustomer(customer);
}



@GetMapping
    public List<Customer> getCustomer(){
    return  customerService.findallCustomer();
}

@GetMapping("/{id}")
public Optional<Customer> getCustomerbyid(@PathVariable Long id){
    return customerService.GetCustomerById(id);
}


@DeleteMapping("/{id}")
    public void DeleteCustomerbyId(@PathVariable Long id){
    customerService.DeleteCustomer(id);
}



}
