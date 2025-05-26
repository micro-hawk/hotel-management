package com.spring.controller;

import java.util.List;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.entity.Response;
import com.spring.entity.Room;
import com.spring.exception.RoomAlreadyExists;
import com.spring.exception.RoomNotFoundException;
import com.spring.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/inventory")
public class RoomController {

    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    private RoomService roomService;

    // addRoom
    @PostMapping("/addRoom")
    public Response addRoom(@RequestBody Room room) {
        logger.info("Request to add a new room: {}", room);
        Room result;
        try {
            result = roomService.addRoom(room);
            logger.info("Room added successfully: {}", result);
            Response response = new Response.Builder().setSuccess(true).setRoom(result)
                    .setMessage("Room Saved Successfully").setCode(HttpStatus.SC_OK).build();
            return response;
        } catch (RoomAlreadyExists e) {
            logger.error("Room already exists: {}", room.getRoomNumber());
            Response response = new Response.Builder().setSuccess(false).setRoom(room)
                    .setMessage("Room Number already exists").setCode(HttpStatus.SC_BAD_REQUEST).build();
            return response;
        }
    }

    // updateRoom
    @PostMapping("/updateRoom/{id}")
    public Response updateRoomResponse(@RequestBody Room room, @PathVariable int id) {
        logger.info("Request to update room with id {}: {}", id, room);
        return updateRoom(room, id);
    }

    @PutMapping("/updateRoom/{id}")
    public Response updateRoom(@RequestBody Room room, @PathVariable int id) {
        try {
            logger.info("Updating room with id {}: {}", id, room);
            Room result = roomService.updateRoom(room, id);
            logger.info("Room updated successfully: {}", result);
            Response response = new Response.Builder().setSuccess(true).setRoom(result)
                    .setMessage("Room Updated Successfully").setCode(HttpStatus.SC_OK).build();
            return response;
        } catch (RoomNotFoundException e) {
            logger.error("Room not found with id {}: {}", id, e.getMessage());
            Response response = new Response.Builder().setSuccess(false).setRoom(room).setMessage(e.getMessage())
                    .setCode(HttpStatus.SC_BAD_REQUEST).build();
            return response;
        }
    }

    // deleteRoom
    @GetMapping("/deleteRoom/{id}")
    public Response deleteRoomResponse(@PathVariable int id) {
        logger.info("Request to delete room with id {}", id);
        return deleteRoom(id);
    }

    @DeleteMapping("/deleteRoom/{id}")
    public Response deleteRoom(@PathVariable int id) {
        try {
            logger.info("Deleting room with id {}", id);
            Room result = roomService.deleteRoom(id);
            logger.info("Room deleted successfully: {}", result);
            Response response = new Response.Builder().setSuccess(true).setRoom(result)
                    .setMessage("Room deleted Successfully").setCode(HttpStatus.SC_OK).build();
            return response;
        } catch (RoomNotFoundException e) {
            logger.error("Room not found for deletion with id {}: {}", id, e.getMessage());
            Response response = new Response.Builder().setSuccess(false).setRoom(null).setMessage(e.getMessage())
                    .setCode(HttpStatus.SC_BAD_REQUEST).build();
            return response;
        }
    }

    // get available rooms
    @GetMapping("/getAvailableRooms")
    public List<Room> getAvailableRooms() {
        logger.info("Request to fetch available rooms");
        List<Room> rooms = roomService.getAvailableRooms();
        logger.info("Fetched {} available rooms", rooms.size());
        return rooms;
    }

    @PostMapping("/setAvailabilityAndReservationId")
    public Response setAvailabilityAndReservationId(@RequestBody Room room) {
        try {
            logger.info("Setting availability and reservation ID for room: {}", room);
            Room result = roomService.setAvailabilityAndReservationId(room);
            logger.info("Room availability and reservation ID updated successfully: {}", result);
            Response response = new Response.Builder().setSuccess(true).setRoom(result)
                    .setMessage("Room availability status updated successfully").setCode(HttpStatus.SC_OK).build();
            return response;
        } catch (RoomNotFoundException e) {
            logger.error("Room not found when setting availability: {}", room.getRoomNumber());
            Response response = new Response.Builder().setSuccess(false).setRoom(null).setMessage(e.getMessage())
                    .setCode(HttpStatus.SC_BAD_REQUEST).build();
            return response;
        }
    }

    @GetMapping("/getRoomByRoomNumber/{roomNumber}")
    public Response getRoomByRoomNumber(@PathVariable String roomNumber) {
        try {
            logger.info("Fetching room by room number: {}", roomNumber);
            Room result = roomService.getRoomByRoomNumber(roomNumber);
            logger.info("Fetched room data successfully: {}", result);
            Response response = new Response.Builder().setSuccess(true).setRoom(result)
                    .setMessage("Room data fetched successfully").setCode(HttpStatus.SC_OK).build();
            return response;
        } catch (RoomNotFoundException e) {
            logger.error("Room not found with room number {}: {}", roomNumber, e.getMessage());
            Response response = new Response.Builder().setSuccess(false).setRoom(null).setMessage(e.getMessage())
                    .setCode(HttpStatus.SC_BAD_REQUEST).build();
            return response;
        }
    }

    @PostMapping("/setRoomAvailabillity/{roomNumber}/{availability}")
    public Response setRoomAvailabillity(@PathVariable String roomNumber, @PathVariable boolean availability) {
        try {
            logger.info("Setting room availability for room {}: {}", roomNumber, availability);
            boolean result = roomService.setRoomAvailabillity(roomNumber, availability);
            logger.info("Room availability status updated: {}", availability);
            Response response = new Response.Builder().setSuccess(result).setRoom(null)
                    .setMessage("Room availability updated successfully").setCode(HttpStatus.SC_OK).build();
            return response;
        } catch (RoomNotFoundException e) {
            logger.error("Room not found for availability update: {}", roomNumber);
            Response response = new Response.Builder().setSuccess(false).setRoom(null).setMessage(e.getMessage())
                    .setCode(HttpStatus.SC_BAD_REQUEST).build();
            return response;
        }
    }

    // get all rooms available and not available
    @GetMapping("/getAllRooms")
    public List<Room> getAllRooms() {
        logger.info("Request to fetch all rooms");
        List<Room> rooms = roomService.getAllRooms();
        logger.info("Fetched {} rooms", rooms.size());
        return rooms;
    }

}
