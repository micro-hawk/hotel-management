package com.spring.controller;

import org.apache.hc.core5.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.entity.Response;
import com.spring.entity.User;
import com.spring.exception.UserErrorException;
import com.spring.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private Authentication authenticate;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String getAdmin() {
        logger.info("Admin access requested.");
        return "Admin access";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/manager")
    public String getManager() {
        logger.info("Manager access requested.");
        return "Manager Access";
    }

    @PostMapping("/register")
    public Response addUser(@RequestBody User user) {
        logger.info("Registration attempt for user: {}", user.getUsername());
        try {
            authService.saveUser(user);
            Response response = new Response.Builder().setSuccess(true)
                    .setMessage("User Registered Successfully")
                    .setCode(HttpStatus.SC_OK).build();
            logger.info("User {} registered successfully.", user.getUsername());
            return response;
        } catch (UserErrorException e) {
            logger.error("Error registering user {}: {}", user.getUsername(), e.getMessage());
            Response response = new Response.Builder().setSuccess(false)
                    .setMessage(e.getMessage())
                    .setCode(HttpStatus.SC_BAD_REQUEST).build();
            return response;
        }
    }

    @PostMapping("/token")
    public Response getToken(@RequestBody User user) {
        logger.info("Token generation attempt for user: {}", user.getUsername());
        try {
            authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            if (authenticate.isAuthenticated()) {
                String token = authService.generateToken(user.getUsername());
                logger.info("Token generated successfully for user: {}", user.getUsername());
                Response response = new Response.Builder().setSuccess(true)
                        .setMessage("Token Generated Successfully")
                        .setToken(token).setCode(HttpStatus.SC_OK).build();
                return response;
            } else {
                logger.warn("Authentication failed for user: {}", user.getUsername());
                Response response = new Response.Builder().setSuccess(false)
                        .setMessage("Failed to generate Token")
                        .setToken(null).setCode(HttpStatus.SC_BAD_REQUEST).build();
                return response;
            }
        } catch (Exception e) {
            logger.error("Error generating token for user {}: {}", user.getUsername(), e.getMessage());
            Response response = new Response.Builder().setSuccess(false)
                    .setMessage("Invalid username or password")
                    .setToken(null).setCode(HttpStatus.SC_BAD_REQUEST).build();
            return response;
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        logger.info("Token validation requested for token: {}", token);
        try {
            authService.validateToken(token);
            logger.info("Token is valid.");
            return "Token is valid";
        } catch (Exception e) {
            logger.error("Invalid token: {}", token, e);
            return "Invalid token";
        }
    }
}
