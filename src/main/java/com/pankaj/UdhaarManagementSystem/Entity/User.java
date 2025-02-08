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
    @Column(nullable = false)
    private String name;
    @Column(unique = true, length = 10)
    private String phone_no;
    private String address;
    private String email;
//    @OneToMany(mappedBy = "user")
// private List<Customer>customers;



}
