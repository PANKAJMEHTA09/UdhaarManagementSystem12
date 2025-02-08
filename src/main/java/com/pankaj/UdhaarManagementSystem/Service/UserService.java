package com.pankaj.UdhaarManagementSystem.Service;

import com.pankaj.UdhaarManagementSystem.DTO.UserDTO;
import com.pankaj.UdhaarManagementSystem.Entity.User;
import com.pankaj.UdhaarManagementSystem.Repo.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
      @Autowired
     private ModelMapper modelMapper;


    public UserDTO Adduser(UserDTO userDTO){
   User user = modelMapper.map(userDTO, User.class);
    User savedUser = userRepo.save(user);
    return modelMapper.map(savedUser, UserDTO.class);

    }

    public void DeleteUser(long id){
        userRepo.deleteById(id);
    }

    public List<UserDTO>getAllUser(){
        List<User>users =userRepo.findAll();
        List<UserDTO>userDTOs =new ArrayList<>();
        for(User user : users){
            UserDTO userDTO =modelMapper.map(user,UserDTO.class);
            userDTOs.add(userDTO);
        }
    return userDTOs;


    }

    public Optional<UserDTO> getUserById(Long id) {
        Optional<User> userOptional = userRepo.findById(id);

        if (userOptional.isPresent()) {
            return Optional.ofNullable(modelMapper.map(userOptional.get(), UserDTO.class));
        } else {
            return null; 
        }
    }












}
