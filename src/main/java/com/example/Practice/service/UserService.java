package com.example.Practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Practice.dto.LoginRequest;
import com.example.Practice.entity.User;
import com.example.Practice.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;
import com.example.Practice.security.JwtService;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
@Autowired
private BCryptPasswordEncoder passwordEncoder;

@Autowired
private JwtService jwtService;
    public User registerUser(User user) {

    if(user.getRole() == null) {
        user.setRole("USER");
    }

    user.setPassword(
        passwordEncoder.encode(user.getPassword())
    );

    return userRepository.save(user);
}
    public String loginUser(LoginRequest loginRequest) {

    Optional<User> optionalUser =
            userRepository.findByEmail(loginRequest.getEmail());

    if (optionalUser.isEmpty()) {

        return "User not found";
    }

    User user = optionalUser.get();

    boolean isPasswordCorrect =
            passwordEncoder.matches(
                    loginRequest.getPassword(),
                    user.getPassword()
            );

    if (!isPasswordCorrect) {

        return "Invalid Password";
    }

return jwtService.generateToken(
        user.getEmail(),
        user.getRole()
);
}
}
