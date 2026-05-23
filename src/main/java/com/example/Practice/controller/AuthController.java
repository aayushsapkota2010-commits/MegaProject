package com.example.Practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Practice.entity.User;
import com.example.Practice.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.Practice.dto.LoginRequest;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.Practice.dto.ResetPasswordRequest;
import com.example.Practice.repository.UserRepository;
import com.example.Practice.store.OtpStore;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
private UserRepository userRepository;

@Autowired
private PasswordEncoder passwordEncoder;
    @PostMapping("/signup")
    public ResponseEntity<User> registerUser(@RequestBody User user) {

        User savedUser=userService.registerUser(user);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
    
    @PostMapping("/login")
public ResponseEntity<String> loginUser(
        @RequestBody LoginRequest loginRequest) {

    String response =
            userService.loginUser(loginRequest);

    return ResponseEntity.ok(response);
}


@PostMapping("/reset-password")
public ResponseEntity<String> resetPassword(

        @RequestBody ResetPasswordRequest request) {

    Boolean verified =
            OtpStore.verifiedEmails
                    .get(request.getEmail());

    if (verified == null || !verified) {

        return ResponseEntity.badRequest()
                .body("Email not verified");
    }

    Optional<User> optionalUser =
            userRepository.findByEmail(
                    request.getEmail()
            );

    if (optionalUser.isEmpty()) {

        return ResponseEntity.badRequest()
                .body("User not found");
    }

    User user = optionalUser.get();

    user.setPassword(
            passwordEncoder.encode(
                    request.getNewPassword()
            )
    );

    userRepository.save(user);

    OtpStore.verifiedEmails
            .remove(request.getEmail());

    return ResponseEntity.ok(
            "Password reset successful"
    );
}

}
