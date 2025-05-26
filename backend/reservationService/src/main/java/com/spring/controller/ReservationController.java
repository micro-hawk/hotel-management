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

import com.spring.entity.Reservation;
import com.spring.entity.Response;
import com.spring.entity.Room;
import com.spring.exception.ReservationNotFoundException;
import com.spring.exception.RoomTypeNotAvailable;
import com.spring.service.ReservationService;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class); // Logger instance

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/getAvailableRooms")
    public List<Room> getAvailableRooms() {
        logger.info("Fetching available rooms");

        List<Room> availableRooms = reservationService.getAvailableRooms();
        
        logger.info("Found {} available rooms", availableRooms.size());

        return availableRooms;
    }

    @PostMapping("/addReservation")
    public Response addReservation(@RequestBody Reservation reservation) {
        logger.info("Received request to add reservation: {}", reservation);

        try {
            Reservation result = reservationService.addReservation(reservation);
            if (result != null) {
                logger.info("Reservation added successfully: {}", result);

                Response response = new Response.Builder().setSuccess(true).setReservation(result)
                        .setMessage("Reservation added successfully").setCode(HttpStatus.SC_OK).build();

                return response;
            } else {
                logger.warn("Reservation not added. Room may not be available.");

                Response response = new Response.Builder().setSuccess(false)
                        .setMessage("Reservation not added successfully/Rooms are not available").setReservation(null)
                        .setCode(HttpStatus.SC_BAD_REQUEST).build();
                return response;
            }
        } catch (RoomTypeNotAvailable e) {
            logger.error("Error adding reservation: Room type not available. Error: {}", e.getMessage());

            Response response = new Response.Builder().setSuccess(false).setMessage(e.getMessage()).setReservation(null)
                    .setCode(HttpStatus.SC_BAD_REQUEST).build();
            return response;
        }
    }

    // Updating Reservation details
    @PostMapping("/updateReservation/{reservationId}")
    public Response updateReservationResponse(@RequestBody Reservation reservation, @PathVariable int reservationId) {
        logger.info("Received request to update reservation with ID: {}", reservationId);
        return updateReservation(reservation, reservationId);
    }

    @PutMapping("/updateReservation/{reservationId}")
    public Response updateReservation(@RequestBody Reservation reservation, @PathVariable int reservationId) {
        try {
            logger.info("Updating reservation with ID: {}", reservationId);
            Reservation result = reservationService.updatReservation(reservation, reservationId);
            logger.info("Reservation updated successfully: {}", result);

            Response response = new Response.Builder().setSuccess(true).setReservation(result)
                    .setMessage("Reservation updated successfully").setCode(HttpStatus.SC_OK).build();

            return response;

        } catch (ReservationNotFoundException e) {
            logger.error("Error updating reservation with ID {}: {}", reservationId, e.getMessage());

            Response response = new Response.Builder().setSuccess(false)
                    .setMessage("Reservation not updated successfully").setReservation(null)
                    .setCode(HttpStatus.SC_BAD_REQUEST).build();
            return response;
        }
    }

    // Cancel Reservation
    @GetMapping("/deleteReservation/{reservationId}")
    public Response deleteReservationResponse(@PathVariable int reservationId) {
        logger.info("Received request to delete reservation with ID: {}", reservationId);
        return deleteReservation(reservationId);
    }

    @DeleteMapping("/deleteReservation/{reservationId}")
    public Response deleteReservation(@PathVariable int reservationId) {
        try {
            logger.info("Deleting reservation with ID: {}", reservationId);
            Reservation result = reservationService.deleteReservation(reservationId);
            logger.info("Reservation deleted successfully: {}", result);

            Response response = new Response.Builder().setSuccess(true).setReservation(result)
                    .setMessage("Reservation deleted successfully").setCode(HttpStatus.SC_OK).build();

            return response;

        } catch (ReservationNotFoundException e) {
            logger.error("Error deleting reservation with ID {}: {}", reservationId, e.getMessage());

            Response response = new Response.Builder().setSuccess(false)
                    .setMessage("Reservation not deleted successfully").setReservation(null)
                    .setCode(HttpStatus.SC_BAD_REQUEST).build();
            return response;
        }
    }

    // get Reservation by id
    @GetMapping("/getReservationById/{reservationId}")
    public Response getReservationById(@PathVariable int reservationId) {
        logger.info("Fetching reservation with ID: {}", reservationId);
        
        Reservation result = reservationService.getReservationById(reservationId);
        if (result != null) {
            logger.info("Reservation fetched successfully: {}", result);

            Response response = new Response.Builder().setSuccess(true).setReservation(result)
                    .setMessage("Reservation fetched with id " + reservationId).setCode(HttpStatus.SC_OK).build();

            return response;

        } else {
            logger.warn("Reservation not found with id {}", reservationId);

            Response response = new Response.Builder().setSuccess(false)
                    .setMessage("Reservation not found with id " + reservationId).setReservation(null)
                    .setCode(HttpStatus.SC_BAD_REQUEST).build();
            return response;
        }
    }

    @GetMapping("/getActiveReservations")
    public List<Reservation> getActiveReservations() {
        logger.info("Fetching active reservations");

        List<Reservation> activeReservations = reservationService.getActiveReservations();
        
        logger.info("Found {} active reservations", activeReservations.size());

        return activeReservations;
    }

    @DeleteMapping("/deleteGuest/{guestId}")
    public void deleteGuest(@PathVariable int guestId) {
        logger.info("Request to delete guest with ID: {}", guestId);
        reservationService.deleteGuestById(guestId);
        logger.info("Guest with ID {} deleted successfully", guestId);
    }
}
