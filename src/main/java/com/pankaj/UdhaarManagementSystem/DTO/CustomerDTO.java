package com.pankaj.UdhaarManagementSystem.DTO;


import jakarta.validation.constraints.Email;
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

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;
    private boolean emailVerified;

    private Double totalAmountofDebt;
    private Double totalAmountReturnedDebt;

    public Double getRemainingDebt() {
        return totalAmountofDebt - totalAmountReturnedDebt;
    }



}
