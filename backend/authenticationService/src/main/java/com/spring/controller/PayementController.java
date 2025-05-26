package com.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_RECEPTION')")
public class PayementController {

    private static final Logger logger = LoggerFactory.getLogger(PayementController.class);

    @Autowired
    RestTemplate restTemplate;

    private static final String PAY_BASE_URL = "http://localhost:8006";

    @GetMapping("/payment/{amount}")
    public String Payment(@PathVariable int amount) throws Exception {
        logger.info("Received payment request with amount: {}", amount);
        try {
            String order = restTemplate.getForObject(PAY_BASE_URL + "/payment/{amount}", String.class, amount);
            logger.info("Payment processed successfully for amount: {}", amount);
            return order;
        } catch (Exception e) {
            logger.error("Error occurred while processing payment for amount: {}", amount, e);
            throw new Exception("Payment processing failed", e);
        }
    }
}
