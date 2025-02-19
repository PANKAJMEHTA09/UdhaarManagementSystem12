package com.pankaj.UdhaarManagementSystem.Controller;


import com.pankaj.UdhaarManagementSystem.DTO.CustomerDTO;
import com.pankaj.UdhaarManagementSystem.Entity.Customer;
import com.pankaj.UdhaarManagementSystem.Entity.User;
import com.pankaj.UdhaarManagementSystem.Exception.ResourceNotFoundException;
import com.pankaj.UdhaarManagementSystem.Repo.CustomerRepo;
import com.pankaj.UdhaarManagementSystem.Service.CustomerService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
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
public CustomerDTO AddCustomer(@Valid @RequestBody CustomerDTO customerDTO){
      return customerService.AddCustomer(customerDTO);
}



@GetMapping
    public List<CustomerDTO> getCustomer(){
    return  customerService.findallCustomer();
}

@GetMapping("/{id}")
public CustomerDTO getCustomerbyid(@PathVariable Long id){



    return customerService.GetCustomerById(id);
}


@DeleteMapping("/{id}")
    public void DeleteCustomerbyId(@PathVariable Long id){
    customerService.DeleteCustomer(id);
}



}
