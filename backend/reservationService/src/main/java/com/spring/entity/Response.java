package com.spring.entity;

import java.util.List;

public class Response {

	private boolean success = false;
	private Reservation reservation;
	private Bill bill;
	private String message = null;
	private Room room;
	private RoomService roomService;
	private List<Bill> bills;
	private int code;

	public Response(boolean success, Reservation reservation, Bill bill, String message, Room room,
			RoomService roomService, List<Bill> bills, int code) {
		super();
		this.success = success;
		this.reservation = reservation;
		this.bill = bill;
		this.message = message;
		this.room = room;
		this.roomService = roomService;
		this.bills = bills;
		this.code = code;
	}

	public boolean isSuccess() {
		return success;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public Bill getBill() {
		return bill;
	}

	public String getMessage() {
		return message;
	}

	public Room getRoom() {
		return room;
	}

	public RoomService getRoomService() {
		return roomService;
	}

	public List<Bill> getBills() {
		return bills;
	}

	public int getCode() {
		return code;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public static class Builder {
		private boolean success = false;
		private Reservation reservation;
		private Bill bill;
		private String message = null;
		private Room room;
		private RoomService roomService;
		private List<Bill> bills;
		private int code;

		public Builder setSuccess(boolean success) {
			this.success = success;
			return this;
		}

		public Builder setReservation(Reservation reservation) {
			this.reservation = reservation;
			return this;
		}

		public Builder setBill(Bill bill) {
			this.bill = bill;
			return this;
		}

		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		public Builder setRoom(Room room) {
			this.room = room;
			return this;
		}

		public Builder setRoomService(RoomService roomService) {
			this.roomService = roomService;
			return this;
		}

		public Builder setBills(List<Bill> bills) {
			this.bills = bills;
			return this;
		}

		public Builder setCode(int code) {
			this.code = code;
			return this;
		}

		public Response build() {
			return new Response(success, reservation, bill, message, room, roomService, bills, code);
		}

	}
}