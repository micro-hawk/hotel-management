package com.spring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.entity.User;
import com.spring.exception.UserErrorException;
import com.spring.repository.UserRepository;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    // Save User with Password Encoding
    public User saveUser(User user) throws UserErrorException {
        logger.info("Request received to save user with username: {}", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        logger.info("Password for user {} has been encoded", user.getUsername());
        try {
            User savedUser = repository.save(user);
            logger.info("User with username: {} saved successfully", user.getUsername());
            return savedUser;
        } catch (Exception e) {
            logger.error("Error occurred while saving user with username: {}", user.getUsername(), e);
            throw new UserErrorException("Username and Email Should be unique");
        }
    }

    // Generate JWT Token
    public String generateToken(String username) {
        logger.info("Request received to generate token for username: {}", username);
        String token = jwtService.generateToken(username);
        logger.info("Token generated for username: {}", username);
        return token;
    }

    // Validate JWT Token
    public void validateToken(String token) {
        logger.info("Request received to validate token: {}", token);
        jwtService.validateToken(token);
        logger.info("Token validated successfully: {}", token);
    }
}
