package com.spring.controller;

import java.util.List;

import org.apache.hc.core5.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.entity.Response;
import com.spring.entity.Staff;
import com.spring.exception.StaffNotFoundException;
import com.spring.service.StaffService;

@RestController
@RequestMapping("/inventory")
public class StaffController {

    private static final Logger logger = LoggerFactory.getLogger(StaffController.class); // Logger instance

    @Autowired
    private StaffService staffService;

    // add staff
    @PostMapping("/addStaff")
    public Response addStaff(@RequestBody Staff staff) {
        logger.info("Request to add staff received: {}", staff);
        Staff result = staffService.addStaff(staff);
        if (result != null) {
            logger.info("Staff added successfully: {}", result);
            Response response = new Response.Builder().setSuccess(true).setStaff(result)
                    .setMessage("Staff added Successfully").setCode(HttpStatus.SC_OK).build();
            return response;
        } else {
            logger.error("Staff not saved: Department does not exist or other error for staff: {}", staff);
            Response response = new Response.Builder().setSuccess(false).setStaff(staff)
                    .setMessage("Staff not saved successfully/Department not exist").setCode(HttpStatus.SC_BAD_REQUEST)
                    .build();
            return response;
        }
    }

    // update staff
    @PostMapping("/updateStaff/{id}")
    public Response updateStaffResponse(@RequestBody Staff staff, @PathVariable int id) {
        logger.info("Request to update staff with ID: {} and staff details: {}", id, staff);
        return updateStaff(staff, id);
    }

    @PutMapping("/updateStaff/{id}")
    public Response updateStaff(@RequestBody Staff staff, @PathVariable int id) {
        logger.info("Request to update staff with ID: {}", id);
        try {
            Staff result = staffService.updateStaff(staff, id);
            if (result != null) {
                logger.info("Staff updated successfully: {}", result);
                Response response = new Response.Builder().setSuccess(true).setStaff(result)
                        .setMessage("Staff updated Successfully").setCode(HttpStatus.SC_OK).build();
                return response;
            } else {
                logger.error("Staff not updated: Department id not found for ID: {}", id);
                Response response = new Response.Builder().setSuccess(false).setStaff(result)
                        .setMessage("Staff not updated/Department id not found").setCode(HttpStatus.SC_BAD_REQUEST)
                        .build();
                return response;
            }

        } catch (StaffNotFoundException e) {
            logger.error("Staff not found exception: {}", e.getMessage());
            Response response = new Response.Builder().setSuccess(false).setStaff(staff).setMessage(e.getMessage())
                    .setCode(HttpStatus.SC_BAD_REQUEST).build();
            return response;
        }

    }

    // delete staff
    @GetMapping("/deleteStaff/{id}")
    public Response deleteStaffResponse(@PathVariable int id) {
        logger.info("Request to delete staff with ID: {}", id);
        return deleteStaff(id);
    }

    @DeleteMapping("/deleteStaff/{id}")
    public Response deleteStaff(@PathVariable int id) {
        try {
            logger.info("Request to delete staff with ID: {}", id);
            Staff result = staffService.deleteStaff(id);
            logger.info("Staff deleted successfully: {}", result);
            Response response = new Response.Builder().setSuccess(true).setStaff(result)
                    .setMessage("Staff deleted Successfully").setCode(HttpStatus.SC_OK).build();
            return response;
        } catch (StaffNotFoundException e) {
            logger.error("Staff not found exception during delete: {}", e.getMessage());
            Response response = new Response.Builder().setSuccess(false).setStaff(null).setMessage(e.getMessage())
                    .setCode(HttpStatus.SC_BAD_REQUEST).build();
            return response;
        }
    }

    // view all staff
    @GetMapping("/getAllStaff")
    public List<Staff> getAllStaff() {
        logger.info("Request to get all staff");
        List<Staff> staffList = staffService.getAllStaff();
        logger.info("All staff fetched successfully: {}", staffList.size());
        return staffList;
    }

}
