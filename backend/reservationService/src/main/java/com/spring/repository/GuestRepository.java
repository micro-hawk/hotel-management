package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.Guest;

public interface GuestRepository extends JpaRepository<Guest, Integer> {

}