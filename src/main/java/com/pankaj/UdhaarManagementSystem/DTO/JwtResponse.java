package com.pankaj.UdhaarManagementSystem.DTO;

import com.pankaj.UdhaarManagementSystem.enums.Role;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
    private UserResponse user;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserResponse {
        private Long userId;
        private String name;
        private String email;
        private Role role;
    }
}
