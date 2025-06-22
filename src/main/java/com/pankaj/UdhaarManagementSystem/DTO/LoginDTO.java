package com.pankaj.UdhaarManagementSystem.DTO;

import com.pankaj.UdhaarManagementSystem.Validation.ValidPassword;
import jakarta.validation.constraints.Email;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginDTO {
    @Email
    private String email;
    @ValidPassword
    private String password;
}
