package com.pankaj.UdhaarManagementSystem.Service;

import com.pankaj.UdhaarManagementSystem.DTO.CustomerDTO;
import com.pankaj.UdhaarManagementSystem.DTO.UserDTO;
import com.pankaj.UdhaarManagementSystem.Entity.Customer;
import com.pankaj.UdhaarManagementSystem.Entity.User;
import com.pankaj.UdhaarManagementSystem.Repo.CustomerRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private ModelMapper modelMapper;

    public CustomerDTO AddCustomer(CustomerDTO customerDTO){
      Customer customer =modelMapper.map(customerDTO, Customer.class);
      customer = customerRepo.save(customer);
      return  modelMapper.map(customer, CustomerDTO.class);

    }

     public void DeleteCustomer(Long id){
        customerRepo.deleteById(id);
     }

   public Optional<CustomerDTO>GetCustomerById(Long id){
        Optional<Customer>customer=customerRepo.findById(id);
        if(customer.isPresent()){
            CustomerDTO customerDTO=modelMapper.map(customer.get(), CustomerDTO.class);
            return  Optional.of(customerDTO);
        }
        else {
            return Optional.empty();
        }

   }

   public List<CustomerDTO>findallCustomer(){
       List<CustomerDTO> customerDTOList = new ArrayList<>();
       for (Customer customer : customerRepo.findAll()) {
           customerDTOList.add(modelMapper.map(customer, CustomerDTO.class));
       }






//       List<User>users =userRepo.findAll();
//       List<UserDTO>userDTOs =new ArrayList<>();
//       for(User user : users){
//           UserDTO userDTO =modelMapper.map(user,UserDTO.class);
//           userDTOs.add(userDTO);
//       }
//       return userDTOs;
//
//










       return customerDTOList;
   }


}
