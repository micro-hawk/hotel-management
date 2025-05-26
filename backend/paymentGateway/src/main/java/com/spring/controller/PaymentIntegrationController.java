package com.spring.controller;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@RestController
@CrossOrigin
public class PaymentIntegrationController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentIntegrationController.class); // Logger instance

    @Value("${rzp_key_id}")
    private String keyId;

    @Value("${rzp_key_secret}")
    private String secret;

    @GetMapping("/payment/{amount}")
    public String Payment(@PathVariable int amount) throws RazorpayException {
        logger.info("Request received to initiate payment for amount: {}", amount);

        try {
            RazorpayClient razorpayClient = new RazorpayClient(keyId, secret);
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount * 100);  // in paise
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "order_receipt_11");

            Order order = razorpayClient.orders.create(orderRequest);
            String orderId = order.get("id");

            logger.info("Payment order created successfully with Order ID: {}", orderId);

            // Return as JSON
            JSONObject response = new JSONObject();
            response.put("id", orderId);
            response.put("amount", amount * 100);
            response.put("currency", "INR");

            return response.toString();

        } catch (RazorpayException e) {
            logger.error("Error occurred while creating payment order: {}", e.getMessage());
            throw e;
        }
    }

}
