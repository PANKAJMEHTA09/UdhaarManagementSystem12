package com.pankaj.UdhaarManagementSystem.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone_no;
    private String address;
    private String email;
//    @OneToMany(mappedBy = "user")
// private List<Customer>customers;



}
