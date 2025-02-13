package com.pankaj.UdhaarManagementSystem.DTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {

    private Long id;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private  String phone_no;
    @NotNull(message = "User ID cannot be null")
    private Long userId;


}
