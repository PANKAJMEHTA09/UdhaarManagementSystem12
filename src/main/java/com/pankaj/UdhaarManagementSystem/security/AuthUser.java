package com.pankaj.UdhaarManagementSystem.security;

import com.pankaj.UdhaarManagementSystem.Entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class AuthUser extends User implements UserDetails { //remove extend userinfo and check if its works
    private final User user;

    public AuthUser(User userInfo) {
        this.user = userInfo;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
                new SimpleGrantedAuthority(user.getRole().toString())
        );
    }


}
