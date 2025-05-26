package com.spring.controller;

import java.util.List;

import org.apache.hc.core5.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.entity.Bill;
import com.spring.entity.Report;
import com.spring.entity.Response;
import com.spring.exception.ReservationNotFoundException;
import com.spring.service.BillService;

@RestController
@RequestMapping("/reservation")
public class BillController {

    private static final Logger logger = LoggerFactory.getLogger(BillController.class); // Logger instance

    @Autowired
    private BillService billService;

    @PostMapping("/generateBill")
    public Response generateBill(@RequestBody Bill bill) {
        logger.info("Received request to generate bill for reservation: {}", bill);

        try {
            Bill result = billService.generateBill(bill);

            Response response = new Response.Builder().setSuccess(true).setBill(bill)
                    .setMessage("Bill generated successfully").setCode(HttpStatus.SC_OK).build();

            logger.info("Bill generated successfully for reservation: {}", bill);

            return response;

        } catch (ReservationNotFoundException e) {
            logger.error("Error generating bill for reservation. Error: {}", e.getMessage());

            Response response = new Response.Builder().setSuccess(false).setMessage(e.getMessage())
                    .setReservation(null).setCode(HttpStatus.SC_BAD_REQUEST).build();
            return response;
        }
    }

    @GetMapping("/getNotPaidBills")
    public List<Bill> getNotPaidBills() {
        logger.info("Fetching not paid bills");

        List<Bill> result = billService.getNotPaidBills();
        
        logger.info("Found {} not paid bills", result.size());

        return result;
    }

    @PostMapping("/setPaidForBill/{billId}")
    public Response setPaidForBill(@PathVariable int billId) {
        logger.info("Request to mark bill {} as paid", billId);

        Bill result = billService.setPaidForBill(billId);
        if (result != null) {
            logger.info("Bill {} marked as paid successfully", billId);

            Response response = new Response.Builder().setSuccess(true).setBill(result)
                    .setMessage("Bill Paid successfully").setCode(HttpStatus.SC_OK).build();
            return response;
        } else {
            logger.error("Failed to mark bill {} as paid", billId);

            Response response = new Response.Builder().setSuccess(false).setMessage("Bill not paid successfully")
                    .setBill(null).setCode(HttpStatus.SC_BAD_REQUEST).build();
            return response;
        }
    }

    @GetMapping("/report/{days}")
    public Report generateReport(@PathVariable int days) {
        logger.info("Generating report for the last {} days", days);

        Report report = billService.generateReport(days);

        logger.info("Report generated for the last {} days", days);

        return report;
    }
}
