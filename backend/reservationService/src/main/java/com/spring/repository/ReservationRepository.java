package com.spring.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

	List<Reservation> findByCheckOutDateGreaterThanEqual(Date today);

	List<Reservation> findByBilled(boolean b);

}