package com.spring.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.spring.entity.Response;

@RestController
@RequestMapping("/reservation")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_RECEPTION')")
public class ReservationController {

    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @Autowired
    RestTemplate restTemplate;

    private static final String RESV_BASE_URL = "http://localhost:8000/reservation";

    // RESERVATION CONTROLLER

    @GetMapping("/getAvailableRooms")
    public List<Object> getAvailableRooms() {
        logger.info("Fetching available rooms");
        Object[] rooms = restTemplate.getForObject(RESV_BASE_URL + "/getAvailableRooms", Object[].class);
        logger.info("Available rooms fetched: {}", rooms.length);
        return Arrays.asList(rooms);
    }

    @GetMapping("/getReservationById/{reservationId}")
    public Response getReservationById(@PathVariable int reservationId) {
        logger.info("Fetching reservation with ID: {}", reservationId);
        Response response = restTemplate.getForObject(RESV_BASE_URL + "/getReservationById/{reservationId}", Response.class, reservationId);
        logger.info("Reservation fetched for ID: {}", reservationId);
        return response;
    }

    @GetMapping("/getActiveReservations")
    public List<Object> getActiveReservations() {
        logger.info("Fetching active reservations");
        Object[] rooms = restTemplate.getForObject(RESV_BASE_URL + "/getActiveReservations", Object[].class);
        logger.info("Active reservations fetched: {}", rooms.length);
        return Arrays.asList(rooms);
    }

    @PostMapping("/addReservation")
    public Response addReservation(@RequestBody Object reservation) {
        logger.info("Adding a new reservation");
        Response response = restTemplate.postForObject(RESV_BASE_URL + "/addReservation", reservation, Response.class);
        logger.info("Reservation added successfully");
        return response;
    }

    @PutMapping("/updateReservation/{reservationId}")
    public Response updateReservation(@RequestBody Object reservation, @PathVariable int reservationId) {
        logger.info("Updating reservation with ID: {}", reservationId);
        Response response = restTemplate.postForObject(RESV_BASE_URL + "/updateReservation/{reservationId}", reservation, Response.class, reservationId);
        logger.info("Reservation updated successfully for ID: {}", reservationId);
        return response;
    }

    @DeleteMapping("/deleteReservation/{reservationId}")
    public Response deleteReservation(@PathVariable int reservationId) {
        logger.info("Deleting reservation with ID: {}", reservationId);
        Response response = restTemplate.getForObject(RESV_BASE_URL + "/deleteReservation/{reservationId}", Response.class, reservationId);
        logger.info("Reservation deleted successfully for ID: {}", reservationId);
        return response;
    }

    // BILL CONTROLLER
    @PostMapping("/generateBill")
    public Response generateBill(@RequestBody Object bill) {
        logger.info("Generating bill");
        Response response = restTemplate.postForObject(RESV_BASE_URL + "/generateBill", bill, Response.class);
        logger.info("Bill generated successfully");
        return response;
    }

    @GetMapping("/getNotPaidBills")
    public List<Object> getNotPaidBills() {
        logger.info("Fetching not paid bills");
        Object[] bills = restTemplate.getForObject(RESV_BASE_URL + "/getNotPaidBills", Object[].class);
        logger.info("Not paid bills fetched: {}", bills.length);
        return Arrays.asList(bills);
    }

    @PostMapping("/setPaidForBill/{billId}")
    public Response setPaidForBill(@PathVariable int billId) {
        logger.info("Setting bill with ID: {} as paid", billId);
        Response response = restTemplate.postForObject(RESV_BASE_URL + "/setPaidForBill/{billId}", null, Response.class, billId);
        logger.info("Bill with ID: {} set as paid", billId);
        return response;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/report/{days}")
    public Object getReport(@PathVariable int days) {
        logger.info("Fetching report for last {} days", days);
        Object report = restTemplate.getForObject(RESV_BASE_URL + "/report/{days}", Object.class, days);
        logger.info("Report fetched for last {} days", days);
        return report;
    }

    // Guest Controller
    @DeleteMapping("/deleteGuest/{guestId}")
    public void deleteGuest(@PathVariable int guestId) {
        logger.info("Deleting guest with ID: {}", guestId);
        restTemplate.delete(RESV_BASE_URL + "/deleteGuest/{guestId}", guestId);
        logger.info("Guest with ID: {} deleted", guestId);
    }
}
