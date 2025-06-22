package com.pankaj.UdhaarManagementSystem.Controller;


import com.pankaj.UdhaarManagementSystem.DTO.JwtResponse;
import com.pankaj.UdhaarManagementSystem.DTO.LoginDTO;
import com.pankaj.UdhaarManagementSystem.DTO.RefreshTokenRequest;
import com.pankaj.UdhaarManagementSystem.DTO.UserDTO;
import com.pankaj.UdhaarManagementSystem.Entity.User;
import com.pankaj.UdhaarManagementSystem.Service.UserService;
import com.pankaj.UdhaarManagementSystem.security.Interface.TokenBlacklistServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenBlacklistServiceInterface tokenBlacklistService;


    @Operation(summary = "Login", description = "Logs in the user and returns JWT access and refresh tokens")
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> Login(@Valid @RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(userService.login(loginDTO));
    }

    @Operation(summary = "Refresh JWT token", description = "Generates a new access token using the refresh token")
    @PostMapping("/refreshToken")
    public ResponseEntity<JwtResponse> RefreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return new ResponseEntity<>(userService.getAccessTokenByRefreshToken(refreshTokenRequest), HttpStatus.OK);
    }

    @PostMapping("/logout")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            tokenBlacklistService.addToBlacklist(token);
        }
        // Clear any session-related data if necessary
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "BearerAuth")
    public UserDTO getUser(@PathVariable Long id) {
        log.info("Fetching user with ID: {}", id);
        return userService.getUserById(id);
    }

//    @GetMapping("/username/{username}")
//    public UserDTO getUserByUsername(@PathVariable String username) {
//        return userService.getUserByUsername(username);
//    }


    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "BearerAuth")
    public void deleteUser(@PathVariable Long id) {

        log.info("Deleting user with ID: {}", id);
        userService.DeleteUser(id);
        log.info("User with ID: {} deleted successfully", id);
    }


//    @GetMapping
//    public List<UserDTO>getAllUser(){
//        return  userService.getAllUser();
//
//    }

    @GetMapping("/all")
    @SecurityRequirement(name = "BearerAuth")
    public Page<User> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        log.info("Fetching all users - Page: {}, Size: {}, SortBy: {}, SortDir: {}", page, size, sortBy, sortDir);
        return userService.getAllUsers(page, size, sortBy, sortDir);
    }


    @GetMapping("/count")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<Long> getTotalUsers() {
        log.info("Fetching total user count");
        return ResponseEntity.ok(userService.getTotalUsers());
    }


    @PostMapping("/register")
    public UserDTO addUser(@Valid @RequestBody UserDTO userDTO) {
        log.info("Adding new user: {}", userDTO);
        UserDTO savedUser = userService.Adduser(userDTO);
        log.info("User added successfully: {}", savedUser);
        return savedUser;

    }

//    @GetMapping("/email/{email}")
//    public ResponseEntity<List<User>> getUserByEmail(@PathVariable String email) {
//        List<User> users = userService.getUserByEmail(email);
//        return ResponseEntity.ok(users);
//    }

}
