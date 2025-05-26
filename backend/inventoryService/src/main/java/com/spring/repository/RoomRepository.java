package com.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {

	List<Room> findByAvailable(boolean available);

	Room findByRoomNumber(String roomNumber);

}
