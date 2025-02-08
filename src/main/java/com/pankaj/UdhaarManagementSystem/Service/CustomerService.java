package com.pankaj.UdhaarManagementSystem.Service;

import com.pankaj.UdhaarManagementSystem.Entity.Customer;
import com.pankaj.UdhaarManagementSystem.Repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    public Customer AddCustomer(Customer customer){
      return   customerRepo.save(customer);

    }

     public void DeleteCustomer(Long id){
        customerRepo.deleteById(id);
     }

   public Optional<Customer>GetCustomerById(Long id){
        return customerRepo.findById(id);

   }

   public List<Customer>findallCustomer(){
        return customerRepo.findAll();
   }


}
