package com.pankaj.UdhaarManagementSystem.Service;

import com.pankaj.UdhaarManagementSystem.DTO.CustomerDTO;
import com.pankaj.UdhaarManagementSystem.DTO.UserDTO;
import com.pankaj.UdhaarManagementSystem.Entity.Customer;
import com.pankaj.UdhaarManagementSystem.Entity.User;
import com.pankaj.UdhaarManagementSystem.Exception.ResourceAlreadyExistsException;
import com.pankaj.UdhaarManagementSystem.Exception.ResourceNotFoundException;
import com.pankaj.UdhaarManagementSystem.Repo.UserRepo;
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
public class UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
     private ModelMapper modelMapper;




    public UserDTO Adduser(UserDTO userDTO){
        log.info("Attempting to add user with name: {}", userDTO.getName());

        if (!userRepo.findByname(userDTO.getName()).isEmpty()) {
            log.error("User with name '{}' already exists", userDTO.getName());
            throw new ResourceAlreadyExistsException("User with this name already exists");
        }

        User user = modelMapper.map(userDTO, User.class);
        User savedUser = userRepo.save(user);

        log.info("User added successfully with ID: {}", savedUser.getId());
        return modelMapper.map(savedUser, UserDTO.class);
    }


    public long getTotalUsers() {
        long count = userRepo.count();
        log.info("Total users in the system: {}", count);
        return count;
    }


    public Page<User> getAllUsers(int page, int size, String sortBy, String sortDir) {
        log.info("Fetching all users with pagination - Page: {}, Size: {}, SortBy: {}, SortDir: {}", page, size, sortBy, sortDir);

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> userPage = userRepo.findAll(pageable);
        log.info("Total users fetched in current page: {}", userPage.getNumberOfElements());

        return userPage;
    }






    public void DeleteUser(long id){
        log.info("Attempting to delete user with ID: {}", id);

        User user = userRepo.findById(id).orElseThrow(() -> {
            log.error("User not found with ID: {}", id);
            return new ResourceNotFoundException("User not found with this id " + id);
        });

        userRepo.deleteById(id);
        log.info("User deleted successfully with ID: {}", id);
    }

//    public List<UserDTO>getAllUser(){
//        List<User>users =userRepo.findAll();
//        List<UserDTO>userDTOs =new ArrayList<>();
//        for(User user : users){
//            UserDTO userDTO =modelMapper.map(user,UserDTO.class);
//            userDTOs.add(userDTO);
//        }
//    return userDTOs;
//
//
//    }

    public UserDTO getUserById(Long id) {
        log.info("Fetching user with ID: {}", id);

        User user = userRepo.findById(id).orElseThrow(() -> {
            log.error("User not found with ID: {}", id);
            return new ResourceNotFoundException("User not found with this id " + id);
        });

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        log.info("User fetched successfully with ID: {}", id);

        return userDTO;
    }




//
//    public UserDTO getUserByUsername(String username) {
//        User user = userRepo.findByUsernameIgnoreCase(username);
//        return (user != null) ? modelMapper.map(user, UserDTO.class) : null;
//    }









}
