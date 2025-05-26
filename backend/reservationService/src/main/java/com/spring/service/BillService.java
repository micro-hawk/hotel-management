package com.spring.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.communication.InventoryServiceClient;
import com.spring.communication.RoomServiceClient;
import com.spring.entity.Bill;
import com.spring.entity.OrderedItem;
import com.spring.entity.Report;
import com.spring.entity.Reservation;
import com.spring.entity.Room;
import com.spring.entity.RoomService;
import com.spring.exception.ReservationNotFoundException;
import com.spring.repository.BillRepository;

@Service
public class BillService {

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private InventoryServiceClient inventoryServiceClient;

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private RoomServiceClient roomServiceClient;

	private static final Logger logger = LoggerFactory.getLogger(BillService.class);

	public Bill generateBill(Bill bill) throws ReservationNotFoundException {

		// call the inventory-service to get room object , extract room price
		String roomNumber = bill.getRoomNumber();
		Room room = inventoryServiceClient.getRoomByRoomNumber(roomNumber).getRoom();

		if (room.getReservationId() == 0)
			throw new ReservationNotFoundException("Reservation not found for room number " + roomNumber);

		Reservation reservation = reservationService.getReservationById(room.getReservationId());
		logger.info("testing--------------------------", room.toString());

		// call room-service and get details related to rommservice --- calculate prices
		// // based on items ordered
		RoomService roomService = roomServiceClient.getRoomServiceByRoomNumberAndCompleted(roomNumber, false)
				.getRoomService();

		double roomPrice = room.getPrice() * reservation.getNumberOfNights();
		Double orderedItemPrice = 0.0;
		if (roomService != null) {
			List<OrderedItem> orderedItems = roomService.getOrderedItems();
			orderedItemPrice = orderedItems.stream().map(item -> item.getPrice() * item.getQuantity()).reduce(0.0,
					Double::sum);

		}

		double totalAmount = roomPrice + orderedItemPrice;
		double taxes = totalAmount * 0.18;
		double netAmount = totalAmount + taxes;

		// after bill generated set completed = true in RoomService
		roomServiceClient.setRoomServiceCompleted(roomNumber, true).isSuccess();

		// setting reservation as completed or billed
		boolean billed = reservationService.setReservationBilled(room.getReservationId());

		// after bill generated and room-service completed , then set availability to
		// true
		room.setAvailable(true);
		room.setReservationId(0);
		boolean roomAvailabillity = inventoryServiceClient.setAvailabilityAndReservationId(room).isSuccess();

		boolean checkOutCompleted = false;
		if (roomAvailabillity && billed) {
			checkOutCompleted = true;
		}
		// Generating bill
		Bill result = new Bill.Builder().setRoomNumber(roomNumber).setGuestName(bill.getGuestName())
				.setPhoneNumber(bill.getPhoneNumber()).setBillDate(new Date()).setTotalAmount(totalAmount)
				.setNetAmount(netAmount).setTaxes(taxes).setCheckOutCompleted(checkOutCompleted).build();

		return billRepository.save(result);
	}

	// get unpaid bills
	public List<Bill> getNotPaidBills() {
		return billRepository.findByPaid(false);
	}

	// set paid for bill
	public Bill setPaidForBill(int billId) {
		Bill bill = billRepository.findById(billId).orElse(null);
		if (bill != null) {
			bill.setPaid(true);
			return billRepository.save(bill);
		}
		return bill;
	}

	// Generate Report last 30 days
	public Report generateReport(int days) {
	    Date thirtyDaysAgo = getThirtyDaysAgoDate(days);
	    long reservations = billRepository.countBillsWithinThirtyDays(thirtyDaysAgo);

	    Double revenueObj = billRepository.calculateTotalRevenue(thirtyDaysAgo);
	    double revenue = (revenueObj != null) ? revenueObj : 0.0;

	    return new Report(reservations, revenue);
	}


	// Util methods
	private Date getThirtyDaysAgoDate(int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -days);
		return calendar.getTime();
	}

}