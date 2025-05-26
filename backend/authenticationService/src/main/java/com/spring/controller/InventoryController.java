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
@RequestMapping("/inventory")
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
public class InventoryController {
    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    RestTemplate restTemplate;

    private static final String INV_BASE_URL = "http://localhost:8000/inventory";

    // Item Controller
    @GetMapping("/getItems")
    public List<Object> getItems() {
        logger.info("Fetching all items from the inventory.");
        Object[] items = restTemplate.getForObject(INV_BASE_URL + "/getItems", Object[].class);
        logger.info("Fetched {} items.", items != null ? items.length : 0);
        return Arrays.asList(items);
    }

    @GetMapping("/getItemById/{id}")
    public List<Object> getItemById(@PathVariable int id) {
        logger.info("Fetching item by id: {}", id);
        Object[] item = restTemplate.getForObject(INV_BASE_URL + "/getItemById/{id}", Object[].class, id);
        logger.info("Fetched item: {}", item != null ? Arrays.toString(item) : "Item not found");
        return Arrays.asList(item);
    }

    @PostMapping("/addItem")
    public Response addItem(@RequestBody Object item) {
        logger.info("Adding new item: {}", item);
        Response response = restTemplate.postForObject(INV_BASE_URL + "/addItem", item, Response.class);
        if (response != null && response.getCode() == 200) {
            logger.info("Item added successfully.");
        } else {
            logger.error("Failed to add item.");
        }
        return response;
    }

    @PutMapping("/updateItem/{id}")
    public Response updateItem(@RequestBody Object item, @PathVariable int id) {
        logger.info("Updating item with id: {}. New data: {}", id, item);
        Response response = restTemplate.postForObject(INV_BASE_URL + "/updateItem/{id}", item, Response.class, id);
        if (response != null && response.getCode() == 200) {
            logger.info("Item with id {} updated successfully.", id);
        } else {
            logger.error("Failed to update item with id {}", id);
        }
        return response;
    }

    @DeleteMapping("/deleteItem/{id}")
    public Response deleteItem(@PathVariable int id) {
        logger.info("Deleting item with id: {}", id);
        Response response = restTemplate.getForObject(INV_BASE_URL + "/deleteItem/{id}", Response.class, id);
        if (response != null && response.getCode() == 200) {
            logger.info("Item with id {} deleted successfully.", id);
        } else {
            logger.error("Failed to delete item with id {}", id);
        }
        return response;
    }

    // ROOM CONTROLLER
    @PostMapping("/addRoom")
    public Response addRoom(@RequestBody Object room) {
        logger.info("Adding new room: {}", room);
        Response response = restTemplate.postForObject(INV_BASE_URL + "/addRoom", room, Response.class);
        if (response != null && response.getCode() == 200) {
            logger.info("Room added successfully.");
        } else {
            logger.error("Failed to add room.");
        }
        return response;
    }

    @PutMapping("/updateRoom/{id}")
    public Response updateRoom(@RequestBody Object room, @PathVariable int id) {
        logger.info("Updating room with id: {}. New data: {}", id, room);
        Response response = restTemplate.postForObject(INV_BASE_URL + "/updateRoom/{id}", room, Response.class, id);
        if (response != null && response.getCode() == 200) {
            logger.info("Room with id {} updated successfully.", id);
        } else {
            logger.error("Failed to update room with id {}", id);
        }
        return response;
    }

    @DeleteMapping("/deleteRoom/{id}")
    public Response deleteRoom(@PathVariable int id) {
        logger.info("Deleting room with id: {}", id);
        Response response = restTemplate.getForObject(INV_BASE_URL + "/deleteRoom/{id}", Response.class, id);
        if (response != null && response.getCode() == 200) {
            logger.info("Room with id {} deleted successfully.", id);
        } else {
            logger.error("Failed to delete room with id {}", id);
        }
        return response;
    }

    @GetMapping("/getAllRooms")
    public List<Object> getAllRooms() {
        logger.info("Fetching all rooms.");
        Object[] rooms = restTemplate.getForObject(INV_BASE_URL + "/getAllRooms", Object[].class);
        logger.info("Fetched {} rooms.", rooms != null ? rooms.length : 0);
        return Arrays.asList(rooms);
    }

    @GetMapping("/getRoomByRoomNumber/{roomNumber}")
    public Response getRoomByRoomNumber(@PathVariable String roomNumber) {
        logger.info("Fetching room by room number: {}", roomNumber);
        Response response = restTemplate.getForObject(INV_BASE_URL + "/getRoomByRoomNumber/{roomNumber}", Response.class, roomNumber);
        logger.info("Fetched room details: {}", response != null ? response.toString() : "Room not found");
        return response;
    }

    // STAFF CONTROLLER
    @PostMapping("/addStaff")
    public Response addStaff(@RequestBody Object staff) {
        logger.info("Adding new staff: {}", staff);
        Response response = restTemplate.postForObject(INV_BASE_URL + "/addStaff", staff, Response.class);
        if (response != null && response.getCode() == 200) {
            logger.info("Staff added successfully.");
        } else {
            logger.error("Failed to add staff.");
        }
        return response;
    }

    @PutMapping("/updateStaff/{id}")
    public Response updateStaff(@RequestBody Object staff, @PathVariable int id) {
        logger.info("Updating staff with id: {}. New data: {}", id, staff);
        Response response = restTemplate.postForObject(INV_BASE_URL + "/updateStaff/{id}", staff, Response.class, id);
        if (response != null && response.getCode() == 200) {
            logger.info("Staff with id {} updated successfully.", id);
        } else {
            logger.error("Failed to update staff with id {}", id);
        }
        return response;
    }

    @DeleteMapping("/deleteStaff/{id}")
    public Response deleteStaff(@PathVariable int id) {
        logger.info("Deleting staff with id: {}", id);
        Response response = restTemplate.getForObject(INV_BASE_URL + "/deleteStaff/{id}", Response.class, id);
        if (response != null && response.getCode() == 200) {
            logger.info("Staff with id {} deleted successfully.", id);
        } else {
            logger.error("Failed to delete staff with id {}", id);
        }
        return response;
    }

    @GetMapping("/getAllStaff")
    public List<Object> getAllStaff() {
        logger.info("Fetching all staff.");
        Object[] staffs = restTemplate.getForObject(INV_BASE_URL + "/getAllStaff", Object[].class);
        logger.info("Fetched {} staff.", staffs != null ? staffs.length : 0);
        return Arrays.asList(staffs);
    }
}
