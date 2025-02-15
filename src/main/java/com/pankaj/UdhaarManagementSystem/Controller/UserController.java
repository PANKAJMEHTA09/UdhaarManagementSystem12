package com.pankaj.UdhaarManagementSystem.Controller;


import com.pankaj.UdhaarManagementSystem.DTO.UserDTO;
import com.pankaj.UdhaarManagementSystem.Entity.User;
import com.pankaj.UdhaarManagementSystem.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
   private UserService userService;

    @GetMapping("/{id}")
    public UserDTO getUser( @PathVariable  Long id){
       return userService.getUserById(id);

    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.DeleteUser(id);
    }


    @GetMapping
    public List<UserDTO>getAllUser(){
        return  userService.getAllUser();

    }

    @PostMapping
    public UserDTO addUser(@Valid @RequestBody UserDTO userDTO){

        return userService.Adduser(userDTO);


    }

}
