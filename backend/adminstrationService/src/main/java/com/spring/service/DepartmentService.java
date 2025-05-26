package com.spring.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.entity.Department;
import com.spring.exception.DepartmentNotFoundException;
import com.spring.repository.DepartmentRepository;

@Service
public class DepartmentService {
	
	private static final Logger logger = LoggerFactory.getLogger(DepartmentService.class);
	
	@Autowired
	private DepartmentRepository departmentRepository;

	public List<Department> getDepartments() {
		logger.info("Fetching all departments.");
		List<Department> departments = departmentRepository.findAll();
        logger.debug("Departments found: {}", departments);
        return departments;
	}

	public Department getDepartmentById(int id) {
		logger.info("Fetching department by ID: {}", id);
        Department department = departmentRepository.findById(id).orElse(null);
        if (department == null) {
            logger.warn("No department found with ID: {}", id);
        } else {
            logger.debug("Department found: {}", department);
        }
        return department;
	}

	public Department saveDepartment(Department department) {
		logger.info("Saving department: {}", department);
        Department saved = departmentRepository.save(department);
        logger.debug("Department saved: {}", saved);
        return saved;
	}

	public boolean deleteDepartment(int id) throws DepartmentNotFoundException{
		logger.info("Deleting department with ID: {}", id);
        Department oldDept = departmentRepository.findById(id).orElse(null);
        if (oldDept != null) {
            departmentRepository.deleteById(id);
            logger.info("Department deleted with ID: {}", id);
            return true;
        } else {
            logger.error("Department not found with ID: {}", id);
            throw new DepartmentNotFoundException("Department not found");
        }
		
	}

	public Department updateDepartment(Department department, int id) throws DepartmentNotFoundException {
		 logger.info("Updating department with ID: {}", id);
	        Department oldDept = departmentRepository.findById(id).orElse(null);

	        if (oldDept != null) {
	            oldDept.setName(department.getName());
	            oldDept.setDescription(department.getDescription());
	            Department updated = departmentRepository.save(oldDept);
	            logger.debug("Department updated: {}", updated);
	            return updated;
	        } else {
	            logger.error("Department not found for update with ID: {}", id);
	            throw new DepartmentNotFoundException("Department not found");
	        }
	}

}
