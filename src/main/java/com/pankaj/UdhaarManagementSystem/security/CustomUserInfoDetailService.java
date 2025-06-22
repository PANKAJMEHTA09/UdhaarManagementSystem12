package com.pankaj.UdhaarManagementSystem.security;


import com.pankaj.UdhaarManagementSystem.Entity.User;
import com.pankaj.UdhaarManagementSystem.Exception.UserNotVerifiedException;
import com.pankaj.UdhaarManagementSystem.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class CustomUserInfoDetailService implements UserDetailsService {

    private final UserRepo userInfoRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = (userInfoRepo.findByEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        log.info("User found with email for login: {}", email);
        if (!user.isVerified()) {
            throw new UserNotVerifiedException("Please verify your email.");
        }

        return new AuthUser(user);
    }

}