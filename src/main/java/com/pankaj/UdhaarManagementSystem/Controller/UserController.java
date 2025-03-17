package com.pankaj.UdhaarManagementSystem.Controller;


import com.pankaj.UdhaarManagementSystem.DTO.CustomerDTO;
import com.pankaj.UdhaarManagementSystem.DTO.UserDTO;
import com.pankaj.UdhaarManagementSystem.Entity.User;
import com.pankaj.UdhaarManagementSystem.Service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/user")
    @Slf4j
public class UserController {

    @Autowired
   private UserService userService;

    @GetMapping("/{id}")
    public UserDTO getUser( @PathVariable  Long id){
        log.info("Fetching user with ID: {}", id);
        return userService.getUserById(id);
    }

//    @GetMapping("/username/{username}")
//    public UserDTO getUserByUsername(@PathVariable String username) {
//        return userService.getUserByUsername(username);
//    }



    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){

        log.info("Deleting user with ID: {}", id);
        userService.DeleteUser(id);
        log.info("User with ID: {} deleted successfully", id);
    }


//    @GetMapping
//    public List<UserDTO>getAllUser(){
//        return  userService.getAllUser();
//
//    }

    @GetMapping
    public Page<User> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        log.info("Fetching all users - Page: {}, Size: {}, SortBy: {}, SortDir: {}", page, size, sortBy, sortDir);
        return userService.getAllUsers(page, size, sortBy, sortDir);
    }



    @GetMapping("/count")
    public ResponseEntity<Long> getTotalUsers() {
        log.info("Fetching total user count");
        return ResponseEntity.ok(userService.getTotalUsers());
    }


    @PostMapping
    public UserDTO addUser(@Valid @RequestBody UserDTO userDTO){
        log.info("Adding new user: {}", userDTO);
        UserDTO savedUser = userService.Adduser(userDTO);
        log.info("User added successfully: {}", savedUser);
        return savedUser;

    }

//    @GetMapping("/email/{email}")
//    public ResponseEntity<List<User>> getUserByEmail(@PathVariable String email) {
//        List<User> users = userService.getUserByEmail(email);
//        return ResponseEntity.ok(users);
//    }

}
