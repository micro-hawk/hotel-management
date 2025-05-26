package com.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.RoomService;

public interface RoomServiceRepository extends JpaRepository<RoomService, Integer> {

	RoomService findByRoomNumberAndCompleted(String roomNumber, boolean completed);

	RoomService findByRoomNumber(String roomNumber);

	List<RoomService> findByCompleted(boolean b);
}