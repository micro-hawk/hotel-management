package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer>{

}
