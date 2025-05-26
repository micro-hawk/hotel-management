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
import org.springframework.web.client.RestTemplate;

import com.spring.entity.Department;
import com.spring.entity.Response;
import com.spring.exception.DepartmentNotFoundException;
import com.spring.service.DepartmentService;

@RestController
@RequestMapping("/department")
public class DepartmentController {
	
	private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);
	
	private static final String RESV_BASE_URL = "http://localhost:8000/reservation";
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	
	@GetMapping("/get")
	public List<Department> getDepartments(){
		logger.info("Fetching all departments.");
		return departmentService.getDepartments();
	}
	
	@GetMapping("/getDepartmentById/{id}")
	public Department getDepartmentById(@PathVariable int id) {
		logger.info("Fetching department with ID: {}", id);
		return departmentService.getDepartmentById(id);
	}
	
	@PostMapping("/save")
	public Response saveDepartment(@RequestBody Department department) {
		logger.info("Saving department: {}", department);
		Department result = departmentService.saveDepartment(department);
		
		if(result != null) {
			logger.info("Department saved successfully: {}", result);
			return new Response.Builder()
					.setSuccess(true)
					.setDepartment(result)
					.setMessage("Department Saved Successfully")
					.setCode(HttpStatus.SC_OK)
					.build();
		}else {
			logger.error("Failed to save department: {}", department);
			return new Response.Builder()
					.setSuccess(false)
					.setDepartment(department)
					.setMessage("Department not Saved Successfully")
					.setCode(HttpStatus.SC_BAD_REQUEST)
					.build();
		}
	}
	
	@PostMapping("/update/{id}")
	public Response updateDepartmentResponse(@RequestBody Department department,@PathVariable int id) {
		return updateDepartment(department,id);
	}
	
	@PutMapping("/update/{id}")
	public Response updateDepartment(@RequestBody Department department,@PathVariable int id) {
		
		try {
			logger.info("Updating department with ID: {}", id);
			Department result = departmentService.updateDepartment(department, id);
			logger.info("Department updated successfully: {}", result);
			return new Response.Builder()
					.setSuccess(true)
					.setDepartment(result)
					.setMessage("Department updated Successfully")
					.setCode(HttpStatus.SC_OK)
					.build();
		} catch (DepartmentNotFoundException e) {
			logger.error("Update failed. Department not found with ID: {}", id);
			return new Response.Builder()
					.setSuccess(false)
					.setDepartment(department)
					.setMessage(e.getMessage())
					.setCode(HttpStatus.SC_BAD_REQUEST)
					.build();
		}
	}
	
	@GetMapping("/delete/{id}")
	  public Response deleteDepartmentResponse(@PathVariable int id) {
	    return deleteDepartment(id);
	  }
	
	@DeleteMapping("/delete/{id}")
	public Response deleteDepartment(@PathVariable int id) {
		try {
			logger.info("Deleting department with ID: {}", id);
			departmentService.deleteDepartment(id);
			logger.info("Department deleted successfully with ID: {}", id);
			return new Response.Builder()
					.setSuccess(true)
					.setDepartment(null)
					.setMessage("Department deleted Successfully")
					.setCode(HttpStatus.SC_OK)
					.build();
		} catch (DepartmentNotFoundException e) {
			logger.error("Delete failed. Department not found with ID: {}", id);
			return new Response.Builder()
					.setSuccess(false)
					.setDepartment(null)
					.setMessage(e.getMessage())
					.setCode(HttpStatus.SC_BAD_REQUEST)
					.build();
		}
		
	}
	
}
