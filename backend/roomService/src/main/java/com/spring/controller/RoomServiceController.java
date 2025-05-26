package com.spring.controller;

import java.util.List;

import org.apache.hc.core5.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.spring.entity.RoomService;
import com.spring.exception.RoomServiceAlreadyExists;
import com.spring.exception.RoomServiceNotFound;
import com.spring.service.RoomServices;

@RestController
@RequestMapping("/room")
public class RoomServiceController {

    private static final Logger logger = LoggerFactory.getLogger(RoomServiceController.class);

    @Autowired
    private RoomServices service;

    // add room service
    @PostMapping("/addRoomService")
    public Response addRoomService(@RequestBody RoomService roomService) {
        logger.info("Attempting to add a new room service: {}", roomService);
        RoomService result;
        try {
            result = service.addRoomService(roomService);

            logger.info("Room service started successfully: {}", result);
            Response response = new Response.Builder().setSuccess(true).setRoomService(result)
                    .setMessage("RoomService Started successfully").setCode(HttpStatus.SC_OK).build();
            return response;

        } catch (RoomServiceAlreadyExists e) {
            logger.error("Room service already exists: {}", e.getMessage());
            Response response = new Response.Builder().setSuccess(false).setMessage(e.getMessage())
                    .setRoomService(null).setCode(HttpStatus.SC_BAD_REQUEST).build();
            return response;
        }
    }

    // update room service
    @PostMapping("/updateRoomService/{serviceId}")
    public Response updateRoomServiceResponse(@RequestBody RoomService roomService, @PathVariable int serviceId) {
        logger.info("Attempting to update room service with ID: {}", serviceId);
        return updateRoomService(roomService, serviceId);
    }

    @PutMapping("/updateRoomService/{serviceId}")
    public Response updateRoomService(@RequestBody RoomService roomService, @PathVariable int serviceId) {
        try {
            RoomService result = service.updateRoomService(roomService, serviceId);
            logger.info("Room service updated successfully with ID: {}", serviceId);
            Response response = new Response.Builder().setSuccess(true).setRoomService(result)
                    .setMessage("RoomService updated successfully").setCode(HttpStatus.SC_OK).build();
            return response;

        } catch (RoomServiceNotFound e) {
            logger.error("Room service not found for update: {}", e.getMessage());
            Response response = new Response.Builder().setSuccess(false).setMessage(e.getMessage())
                    .setRoomService(roomService).setCode(HttpStatus.SC_BAD_REQUEST).build();
            return response;
        }
    }

    // delete room service
    @GetMapping("/deleteRoomService/{serviceId}")
    public Response deleteRoomServiceResponse(@PathVariable int serviceId) {
        logger.info("Attempting to delete room service with ID: {}", serviceId);
        return deleteRoomService(serviceId);
    }

    @DeleteMapping("/deleteRoomService/{serviceId}")
    public Response deleteRoomService(@PathVariable int serviceId) {
        try {
            RoomService result = service.deleteRoomService(serviceId);
            logger.info("Room service deleted successfully with ID: {}", serviceId);
            Response response = new Response.Builder().setSuccess(true).setRoomService(result)
                    .setMessage("RoomService deleted successfully").setCode(HttpStatus.SC_OK).build();
            return response;

        } catch (RoomServiceNotFound e) {
            logger.error("Room service not found for deletion: {}", e.getMessage());
            Response response = new Response.Builder().setSuccess(false).setMessage(e.getMessage())
                    .setRoomService(null).setCode(HttpStatus.SC_BAD_REQUEST).build();
            return response;
        }
    }

    // get roomservice by room number and completed status
    @GetMapping("/getRoomService/{roomNumber}/{completed}")
    public Response getRoomServiceByRoomNumberAndIsCompleted(@PathVariable String roomNumber,
            @PathVariable boolean completed) {
        logger.info("Fetching room service for room number: {} and completed status: {}", roomNumber, completed);
        try {
            RoomService result = service.getRoomServiceByRoomNumberAndIsCompleted(roomNumber, completed);
            logger.info("Room service fetched successfully for room number: {}", roomNumber);
            Response response = new Response.Builder().setSuccess(true).setRoomService(result)
                    .setMessage("Room Service fetched successfully").setCode(HttpStatus.SC_OK).build();
            return response;

        } catch (RoomServiceNotFound e) {
            logger.error("Room service not found: {}", e.getMessage());
            Response response = new Response.Builder().setSuccess(false).setMessage(e.getMessage()).setRoomService(null)
                    .setCode(HttpStatus.SC_BAD_REQUEST).build();
            return response;
        }
    }

    // set roomservice completed by roomNumber
    @PostMapping("/setRoomServiceCompleted/{roomNumber}/{completed}")
    public Response setRoomServiceCompleted(@PathVariable String roomNumber, @PathVariable boolean completed) {
        logger.info("Setting room service completed for room number: {} to status: {}", roomNumber, completed);
        try {
            boolean result = service.setServiceCompleted(roomNumber, completed);
            logger.info("Room service completed successfully for room number: {}", roomNumber);
            Response response = new Response.Builder().setSuccess(result).setRoomService(null)
                    .setMessage("Room Service completed successfully").setCode(HttpStatus.SC_OK).build();
            return response;

        } catch (RoomServiceNotFound e) {
            logger.error("Room service not found for completion: {}", e.getMessage());
            Response response = new Response.Builder().setSuccess(false).setMessage(e.getMessage()).setRoomService(null)
                    .setCode(HttpStatus.SC_BAD_REQUEST).build();
            return response;
        }
    }

    // get ActiveRoomServices
    @GetMapping("/getActiveRoomServices")
    public List<RoomService> getActiveRoomServices() {
        logger.info("Fetching all active room services");
        return service.getActiveRoomServices();
    }

    // delete ordered item by id
    @DeleteMapping("/deleteOrderedItemById/{id}")
    public void deleteOrderedItemById(@PathVariable int id) {
        logger.info("Deleting ordered item with ID: {}", id);
        service.deleteOrderedItemById(id);
    }
}
