package com.pankaj.UdhaarManagementSystem.security;

import com.pankaj.UdhaarManagementSystem.Entity.User;
import com.pankaj.UdhaarManagementSystem.enums.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public JwtService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }


    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();

        String scope = authentication.getAuthorities().toString();

        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        User user = authUser.getUser();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(10, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String generateToken(String email, Role role) {
        Instant now = Instant.now();

        String scope = role.name(); // or however you want to represent roles/authorities

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(10, ChronoUnit.HOURS))
                .subject(email)
                .claim("scope", scope)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String extractUsername(String token) {
        JwtClaimAccessor accessor = decode(token);
        return accessor.getSubject(); // "sub" claim
    }

    public Date extractExpiration(String token) {
        JwtClaimAccessor accessor = decode(token);
        Instant expiresAt = accessor.getExpiresAt(); // "exp" claim
        return expiresAt != null ? Date.from(expiresAt) : null;
    }

    public Date extractIssuedAt(String token) {
        JwtClaimAccessor accessor = decode(token);
        Instant issuedAt = accessor.getIssuedAt(); // "iat" claim
        return issuedAt != null ? Date.from(issuedAt) : null;
    }

    public boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        return expiration != null && expiration.before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private Jwt decode(String token) {
        return jwtDecoder.decode(token); // Jwt implements JwtClaimAccessor
    }


}
