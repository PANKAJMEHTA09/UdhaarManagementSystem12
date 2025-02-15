package com.pankaj.UdhaarManagementSystem.Service;

import com.pankaj.UdhaarManagementSystem.DTO.CustomerDTO;
import com.pankaj.UdhaarManagementSystem.DTO.UserDTO;
import com.pankaj.UdhaarManagementSystem.Entity.Customer;
import com.pankaj.UdhaarManagementSystem.Entity.User;
import com.pankaj.UdhaarManagementSystem.Exception.ResourceAlreadyExistsException;
import com.pankaj.UdhaarManagementSystem.Exception.ResourceNotFoundException;
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



        if(!customerRepo.findByname(customerDTO.getName()).isEmpty()){
            throw new ResourceAlreadyExistsException("user with this name already exist");
        }


      Customer customer =modelMapper.map(customerDTO, Customer.class);
      customer = customerRepo.save(customer);
      return  modelMapper.map(customer, CustomerDTO.class);

    }

     public void DeleteCustomer(Long id){

         Customer customer=customerRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("user not found with this id "));


         customerRepo.deleteById(id);
     }

   public CustomerDTO GetCustomerById(Long id){
        Customer customer=customerRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("user not found with this id "));

       CustomerDTO CustomerDTO =modelMapper.map(customer,CustomerDTO.class);
       return CustomerDTO;

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
