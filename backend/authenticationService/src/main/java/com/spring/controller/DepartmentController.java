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
@RequestMapping("/department")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class DepartmentController {
    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    RestTemplate restTemplate;

    private static final String DEPT_BASE_URL = "http://localhost:8000/department";

    @GetMapping("/report")
    public String getReports() {
        logger.info("Fetching department reports.");
        String report = restTemplate.getForObject(DEPT_BASE_URL + "/report", String.class);
        logger.info("Fetched department reports successfully.");
        return report;
    }

    @GetMapping("/get")
    public List<Object> getDepartments() {
        logger.info("Fetching list of departments.");
        Object[] departments = restTemplate.getForObject(DEPT_BASE_URL + "/get", Object[].class);
        logger.info("Fetched {} departments.", departments != null ? departments.length : 0);
        return Arrays.asList(departments);
    }

    @PostMapping("/save")
    public Response saveDepartment(@RequestBody Object department) {
        logger.info("Saving new department: {}", department);
        Response response = restTemplate.postForObject(DEPT_BASE_URL + "/save", department, Response.class);
        if (response != null && response.getCode() == 200) {
            logger.info("Department saved successfully.");
        } else {
            logger.error("Error saving department.");
        }
        return response;
    }

    @PutMapping("/update/{id}")
    public Response updateDepartment(@RequestBody Object department, @PathVariable int id) {
        logger.info("Updating department with id {}: {}", id, department);
        Response response = restTemplate.postForObject(DEPT_BASE_URL + "/update/{id}", department, Response.class, id);
        if (response != null && response.getCode() == 200) {
            logger.info("Department with id {} updated successfully.", id);
        } else {
            logger.error("Error updating department with id {}", id);
        }
        return response;
    }

    @DeleteMapping("/delete/{id}")
    public Response deleteDepartment(@PathVariable int id) {
        logger.info("Deleting department with id {}", id);
        Response response = restTemplate.getForObject(DEPT_BASE_URL + "/delete/{id}", Response.class, id);
        if (response != null && response.getCode() == 200) {
            logger.info("Department with id {} deleted successfully.", id);
        } else {
            logger.error("Error deleting department with id {}", id);
        }
        return response;
    }
}
