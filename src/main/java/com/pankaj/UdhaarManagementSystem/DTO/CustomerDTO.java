package com.pankaj.UdhaarManagementSystem.DTO;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {

    private Long id;
    private String name;
    private  String phone_no;
    private Long userId;


}
