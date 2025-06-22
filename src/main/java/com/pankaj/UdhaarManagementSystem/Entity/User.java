package com.pankaj.UdhaarManagementSystem.Entity;


import com.pankaj.UdhaarManagementSystem.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @SequenceGenerator(name = "seq", sequenceName = "ex_seq", allocationSize = 1)
    private Long id;

    private String name;
    private String phone_no;
    private String address;


    private String email;
    private String password;
    private boolean isVerified;

    @Enumerated(EnumType.STRING)
    private Role role;


}
