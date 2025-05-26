package com.spring.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.communication.InventoryServiceClient;
import com.spring.entity.Guest;
import com.spring.entity.Reservation;
import com.spring.entity.Room;
import com.spring.exception.ReservationNotFoundException;
import com.spring.exception.RoomTypeNotAvailable;
import com.spring.repository.GuestRepository;
import com.spring.repository.ReservationRepository;

@Service
public class ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private GuestRepository guestRepository;

	@Autowired
	private InventoryServiceClient inventoryServiceClient;

	// Get Available rooms
	public List<Room> getAvailableRooms() {
		return inventoryServiceClient.getAvailableRooms();
	}

	// add new reservation

	public Reservation addReservation(Reservation reservation) throws RoomTypeNotAvailable {
		List<Room> rooms = getAvailableRooms();
		String roomType = reservation.getRoomType();
		if (!rooms.isEmpty()) {
			rooms = rooms.stream().filter(room -> room.getRoomType().equalsIgnoreCase(roomType))
					.collect(Collectors.toList());

		} else {
			return null;
		}

		if (rooms.size() > 0) {
			Room room = rooms.get(0);
			reservation.setRoomNumber(room.getRoomNumber());

			for (Guest guest : reservation.getGuests()) {
				guest.setReservation(reservation);
			}

			// reservation created with id

			reservation = reservationRepository.save(reservation);

			// update room status in inventory to not available and set reservationId
			room.setReservationId(reservation.getId());
			room.setAvailable(false);
			inventoryServiceClient.setAvailabilityAndReservationId(room);
		} else {
			throw new RoomTypeNotAvailable("Room type not available " + roomType);
		}
		return reservation;
	}

	// update Reservation
	public Reservation updatReservation(Reservation reservation, int reservationId)
			throws ReservationNotFoundException {
		Reservation oldReservation = reservationRepository.findById(reservationId).orElse(null);

		if (oldReservation != null) {
			oldReservation.setNumberOfAdults(reservation.getNumberOfAdults());
			oldReservation.setNumberOfChildren(reservation.getNumberOfChildren());
			oldReservation.setNumberOfNights(reservation.getNumberOfNights());
			oldReservation.setCheckInDate(reservation.getCheckInDate());
			oldReservation.setCheckOutDate(reservation.getCheckOutDate());

			// oldReservation.getGuests().forEach(guest -> {
			// guestRepository.deleteById(guest.getId());
			// });

			reservationRepository.save(oldReservation);
			for (Guest guest : reservation.getGuests()) {
				guest.setReservation(oldReservation);
			}

			oldReservation.setGuests(reservation.getGuests());

			return reservationRepository.save(oldReservation);

		} else {
			throw new ReservationNotFoundException("Reservation not Found");
		}
	}

	// delete Reservation
	public Reservation deleteReservation(int reservationId) throws ReservationNotFoundException {
		Reservation reservation = reservationRepository.findById(reservationId).orElse(null);

		if (reservation != null) {
			Room room = new Room.Builder().setRoomNumber(reservation.getRoomNumber()).setAvailable(true)
					.setReservationId(0).build();
			reservationRepository.deleteById(reservationId);
			inventoryServiceClient.setAvailabilityAndReservationId(room);
			return reservation;
		} else {
			throw new ReservationNotFoundException("Reservation not Found");
		}
	}

	// get reservation by id
	public Reservation getReservationById(int reservationId) {
		System.out.println("-----------reservationId " + reservationId);
		return reservationRepository.findById(reservationId).orElse(null);
	}

	// get All Reservation whose checkout date is greater than or equal to today's
	// date

	public List<Reservation> getActiveReservations() {

		// Date today = new Date();
		// return reservationRepository.findByCheckOutDateGreaterThanEqual(today);
		return reservationRepository.findByBilled(false);

	}

	// remove guest by id
	public void deleteGuestById(int id) {
		guestRepository.deleteById(id);
	}

	// updating reservation status for billing done
	public boolean setReservationBilled(int reservationId) {
		Optional<Reservation> reservation = reservationRepository.findById(reservationId);

		if (reservation.isPresent()) {
			reservation.get().setBilled(true);
			reservationRepository.save(reservation.get());
			return true;
		} else {
			return false;
		}
	}
}