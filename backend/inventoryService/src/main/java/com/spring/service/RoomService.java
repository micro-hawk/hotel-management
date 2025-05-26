package com.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.entity.Room;
import com.spring.exception.RoomAlreadyExists;
import com.spring.exception.RoomNotFoundException;
import com.spring.repository.RoomRepository;

@Service
public class RoomService {

	@Autowired
	private RoomRepository roomRepository;

	// add room
	public Room addRoom(Room room) throws RoomAlreadyExists {
		Room roomExist = roomRepository.findByRoomNumber(room.getRoomNumber());
		if (roomExist != null) {
			throw new RoomAlreadyExists("Room Number Already Exists ");
		}
		return roomRepository.save(room);
	}
	// update room

	public Room updateRoom(Room room, int id) throws RoomNotFoundException {
		Room oldRoom = roomRepository.findById(id).orElse(null);

		if (oldRoom != null) {
			oldRoom.setRoomNumber(room.getRoomNumber());
			oldRoom.setAvailable(room.isAvailable());
			oldRoom.setRoomType(room.getRoomType());
			oldRoom.setPrice(room.getPrice());
			return roomRepository.save(oldRoom);
		} else {
			throw new RoomNotFoundException("Room not found");
		}
	}
	// delete room

	public Room deleteRoom(int id) throws RoomNotFoundException {
		Room oldRoom = roomRepository.findById(id).orElse(null);
		if (oldRoom != null) {
			roomRepository.deleteById(id);
			return oldRoom;
		} else {
			throw new RoomNotFoundException("Room not found");
		}
	}

	// get available rooms
	public List<Room> getAvailableRooms() {
		return roomRepository.findByAvailable(true);
	}

	// set room available status and reservationid
	public Room setAvailabilityAndReservationId(Room requestRoom) throws RoomNotFoundException {
		Room room = roomRepository.findByRoomNumber(requestRoom.getRoomNumber());
		if (room != null) {
			room.setAvailable(requestRoom.isAvailable());
			room.setReservationId(requestRoom.getReservationId());
			return roomRepository.save(room);
		} else {
			throw new RoomNotFoundException("Room not found");
		}

	}

	public Room getRoomByRoomNumber(String roomNumber) throws RoomNotFoundException {
		Room room = roomRepository.findByRoomNumber(roomNumber);
		if (room != null) {
			return room;
		} else {
			throw new RoomNotFoundException("Room not found");
		}
	}

	public boolean setRoomAvailabillity(String roomNumber, boolean availability) throws RoomNotFoundException {
		Room room = roomRepository.findByRoomNumber(roomNumber);
		if (room != null) {
			room.setAvailable(availability);
			room.setReservationId("");
			roomRepository.save(room);
			return true;
		} else {
			throw new RoomNotFoundException("Room not found");
		}
	}

	// get all rooms available and not available
	public List<Room> getAllRooms() {
		return roomRepository.findAll();
	}
}