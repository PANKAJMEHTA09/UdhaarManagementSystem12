package com.pankaj.UdhaarManagementSystem.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone_no;
    //    @OneToMany(mappedBy = "customer")
//private List<Payments>payments;
    @ManyToOne
    private User user;

    private String email;
    private boolean verified_email = false;


    private Double totalAmountGiven = 0.0;
    private Double totalAmountReturned = 0.0;


}
