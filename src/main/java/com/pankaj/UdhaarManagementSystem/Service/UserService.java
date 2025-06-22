package com.pankaj.UdhaarManagementSystem.Service;

import com.pankaj.UdhaarManagementSystem.DTO.JwtResponse;
import com.pankaj.UdhaarManagementSystem.DTO.LoginDTO;
import com.pankaj.UdhaarManagementSystem.DTO.RefreshTokenRequest;
import com.pankaj.UdhaarManagementSystem.DTO.UserDTO;
import com.pankaj.UdhaarManagementSystem.Entity.RefreshToken;
import com.pankaj.UdhaarManagementSystem.Entity.User;
import com.pankaj.UdhaarManagementSystem.Exception.*;
import com.pankaj.UdhaarManagementSystem.Repo.UserRepo;
import com.pankaj.UdhaarManagementSystem.enums.Role;
import com.pankaj.UdhaarManagementSystem.security.AuthUser;
import com.pankaj.UdhaarManagementSystem.security.Interface.RefreshTokenRepo;
import com.pankaj.UdhaarManagementSystem.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepo refreshTokenRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;


    public JwtResponse login(LoginDTO loginDTO) {
        log.info("Check point 1");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Authenticated user: {}", authentication.getPrincipal());
        if (authentication.isAuthenticated()) {
            AuthUser authUser = (AuthUser) authentication.getPrincipal();
            User user = authUser.getUser();

            // Extract role from granted authorities
            String roleName = authUser.getAuthorities().stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElseThrow(() -> new IllegalStateException("No authority found for user"));

            Role role = Role.valueOf(roleName); // Convert back to enum

            RefreshToken refreshToken = this.createRefreshToken(loginDTO.getEmail());
            log.info("Refresh token: {} and Now: {}", refreshToken, Instant.now());

            log.info("Token requested for user :{}", authentication.getAuthorities());


            log.info("Login successfully with email: {} Role: {} RefreshToken: {}", loginDTO.getEmail(), role, refreshToken);
            return JwtResponse.builder()
                    .accessToken(jwtService.generateToken(authentication))
                    .refreshToken(refreshToken.getToken())
                    .user(new JwtResponse.UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole()))
                    .build();
        } else {
            throw new UsernameNotFoundException("invalid User Request");
        }
    }


    public JwtResponse getAccessTokenByRefreshToken(RefreshTokenRequest refreshTokenRequest) {
        return this.findByToken(refreshTokenRequest.getRefreshToken())
                .map(this::verifyExpiration)
                .map(refreshToken -> {
                    String email = refreshToken.getEmail();

                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                    String roleName = userDetails.getAuthorities().stream()
                            .findFirst()
                            .map(GrantedAuthority::getAuthority)
                            .orElseThrow(() -> new IllegalStateException("No authority found for user"));


                    String accessToken = jwtService.generateToken(email, Role.valueOf(roleName));
                    return JwtResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequest.getRefreshToken())
                            .build();
                }).orElseThrow(() -> new RefreshTokenNotFoundException("Refresh token is not in database"));
    }


    public UserDTO Adduser(UserDTO userDTO) {
        log.info("Attempting to add user with name: {}", userDTO.getName());

        checkNameExists(userDTO);
        this.checkEmailExists(userDTO.getEmail());

        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setVerified(true);  //later change it when add email registration
        user.setEmail(userDTO.getEmail().toLowerCase());
        user.setRole(Role.ROLE_USER);
        User savedUser = userRepo.save(user);

        log.info("User added successfully with ID: {}", savedUser.getId());
        return modelMapper.map(savedUser, UserDTO.class);
    }


    public long getTotalUsers() {
        long count = userRepo.count();
        log.info("Total users in the system: {}", count);
        return count;
    }


    public Page<User> getAllUsers(int page, int size, String sortBy, String sortDir) {
        log.info("Fetching all users with pagination - Page: {}, Size: {}, SortBy: {}, SortDir: {}", page, size, sortBy, sortDir);

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> userPage = userRepo.findAll(pageable);
        log.info("Total users fetched in current page: {}", userPage.getNumberOfElements());

        return userPage;
    }


    public void DeleteUser(long id) {
        log.info("Attempting to delete user with ID: {}", id);

        User user = userRepo.findById(id).orElseThrow(() -> {
            log.error("User not found with ID: {}", id);
            return new ResourceNotFoundException("User not found with this id " + id);
        });

        userRepo.deleteById(id);
        log.info("User deleted successfully with ID: {}", id);
    }

//    public List<UserDTO>getAllUser(){
//        List<User>users =userRepo.findAll();
//        List<UserDTO>userDTOs =new ArrayList<>();
//        for(User user : users){
//            UserDTO userDTO =modelMapper.map(user,UserDTO.class);
//            userDTOs.add(userDTO);
//        }
//    return userDTOs;
//
//
//    }

    public UserDTO getUserById(Long id) {
        log.info("Fetching user with ID: {}", id);

        User user = userRepo.findById(id).orElseThrow(() -> {
            log.error("User not found with ID: {}", id);
            return new ResourceNotFoundException("User not found with this id " + id);
        });

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        log.info("User fetched successfully with ID: {}", id);

        return userDTO;
    }


    //
//    public UserDTO getUserByUsername(String username) {
//        User user = userRepo.findByUsernameIgnoreCase(username);
//        return (user != null) ? modelMapper.map(user, UserDTO.class) : null;
//    }


    private void checkEmailExists(String email) {
        if (userRepo.existsByEmail(email)) {
//            kafkaService.publishToKafkaAsync("SendAlreadyRegisteredEmail", userRegistrationDTO.getUserId(), userRegistrationDTO.toString());
            throw new EmailAlreadyExists("Email already exists. Please use another email.");
        }
    }

    private void checkNameExists(UserDTO userDTO) {
        if (!userRepo.findByname(userDTO.getName()).isEmpty()) {
            log.error("User with name '{}' already exists", userDTO.getName());
            throw new ResourceAlreadyExistsException("User with this name already exists");
        }
    }

    //-------------------------Security----------------------------------------------------------------
    private RefreshToken createRefreshToken(String email) {
        User userInfo = userRepo.findByEmail(email).orElseThrow(() -> {
            log.error("User not found with email: {}", email);
            return new ResourceNotFoundException("User not found with this id " + email);
        });
        Optional<RefreshToken> existingTokenOpt = refreshTokenRepo.findByEmail(userInfo.getEmail());
        RefreshToken refreshToken;
        if (existingTokenOpt.isPresent()) {
            refreshToken = existingTokenOpt.get();
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken.setExpiryDate(Instant.now().plus(3,ChronoUnit.DAYS));
        } else {
            refreshToken = RefreshToken.builder()
                    .token(UUID.randomUUID().toString())
                    .expiryDate(Instant.now().plus(3, ChronoUnit.DAYS))
                    .email(userInfo.getEmail())
                    .build();
        }
        return refreshTokenRepo.save(refreshToken);
    }

    private Optional<RefreshToken> findByToken(String refreshToken) {
        return refreshTokenRepo.findByToken(refreshToken);
    }

    private RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            log.info("Refresh token is expiry: {} and Now: {}", refreshToken.getExpiryDate(), Instant.now());
            refreshTokenRepo.delete(refreshToken);
            throw new RefreshTokenExpiredException(refreshToken.getToken() + " Refresh Token was expired.");

        }
        return refreshToken;
    }


    private User getAuthenticatedUser() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = jwt.getId(); // or your actual claim name

        return userRepo.findByEmail(email).orElseThrow(() -> {
            log.error("User not found with email: {}", email);
            return new ResourceNotFoundException("User not found with this id " + email);
        });
    }

}
