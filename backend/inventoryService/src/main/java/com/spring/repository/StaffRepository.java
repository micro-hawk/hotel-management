package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.Staff;

public interface StaffRepository extends JpaRepository<Staff, Integer> {

}