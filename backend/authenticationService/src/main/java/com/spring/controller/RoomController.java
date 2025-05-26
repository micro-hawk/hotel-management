package com.spring.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.spring.entity.Response;

@RestController
@RequestMapping("/room")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_RECEPTION')")
public class RoomController {

    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    RestTemplate restTemplate;

    private static final String ROOM_BASE_URL = "http://localhost:8000/room";

    // Add Room Service
    @PostMapping("/addRoomService")
    public Response addRoomService(@RequestBody Object roomService) {
        logger.info("Request received to add room service: {}", roomService);
        Response response = restTemplate.postForObject(ROOM_BASE_URL + "/addRoomService", roomService, Response.class);
        logger.info("Room service added successfully: {}", response);
        return response;
    }

    // Update Room Service
    @PutMapping("/updateRoomService/{serviceId}")
    public Response updateRoomService(@RequestBody Object roomService, @PathVariable int serviceId) {
        logger.info("Request received to update room service with ID: {}", serviceId);
        Response response = restTemplate.postForObject(ROOM_BASE_URL + "/updateRoomService/{serviceId}", roomService, Response.class, serviceId);
        logger.info("Room service with ID: {} updated successfully: {}", serviceId, response);
        return response;
    }

    // Delete Room Service
    @DeleteMapping("/deleteRoomService/{serviceId}")
    public Response deleteRoomService(@PathVariable int serviceId) {
        logger.info("Request received to delete room service with ID: {}", serviceId);
        Response response = restTemplate.getForObject(ROOM_BASE_URL + "/deleteRoomService/{serviceId}", Response.class, serviceId);
        logger.info("Room service with ID: {} deleted successfully: {}", serviceId, response);
        return response;
    }

    // Get Active Room Services
    @GetMapping("/getActiveRoomServices")
    public List<Object> getActiveRoomServices() {
        logger.info("Request received to fetch active room services");
        Object[] services = restTemplate.getForObject(ROOM_BASE_URL + "/getActiveRoomServices", Object[].class);
        logger.info("Fetched {} active room services", services.length);
        return Arrays.asList(services);
    }

    // Get Room Service by Room Number and Completion Status
    @GetMapping("/getRoomService/{roomNumber}/{completed}")
    public Response getRoomServiceByRoomNumberAndIsCompleted(@PathVariable String roomNumber,
            @PathVariable boolean completed) {
        logger.info("Request received to fetch room service for room number: {} with completed status: {}", roomNumber, completed);
        Response response = restTemplate.getForObject(ROOM_BASE_URL + "/getRoomService/{roomNumber}/{completed}", Response.class,
                roomNumber, completed);
        logger.info("Fetched room service for room number: {}: {}", roomNumber, response);
        return response;
    }

    // Delete Ordered Item by ID
    @DeleteMapping("/deleteOrderedItemById/{id}")
    public void deleteOrderedItemById(@PathVariable int id) {
        logger.info("Request received to delete ordered item with ID: {}", id);
        restTemplate.delete(ROOM_BASE_URL + "/deleteOrderedItemById/{id}", id);
        logger.info("Ordered item with ID: {} deleted successfully", id);
    }
}
