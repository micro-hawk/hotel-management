package com.spring.entity;

public class Room {

	private int id;
	private String roomNumber;
	private String roomType;
	private double price;
	private boolean available;
	private int reservationId;

	public Room() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Room(int id, String roomNumber, String roomType, double price, boolean available, int reservationId) {
		super();
		this.id = id;
		this.roomNumber = roomNumber;
		this.roomType = roomType;
		this.price = price;
		this.available = available;
		this.reservationId = reservationId;
	}

	public int getId() {
		return id;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public String getRoomType() {
		return roomType;
	}

	public double getPrice() {
		return price;
	}

	public boolean isAvailable() {
		return available;
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public static class Builder {
		private int id;
		private String roomNumber;
		private String roomType;
		private double price;
		private boolean available;
		private int reservationId;

		public Builder setId(int id) {
			this.id = id;
			return this;
		}

		public Builder setRoomNumber(String roomNumber) {
			this.roomNumber = roomNumber;
			return this;
		}

		public Builder setRoomType(String roomType) {
			this.roomType = roomType;
			return this;
		}

		public Builder setPrice(double price) {
			this.price = price;
			return this;
		}

		public Builder setAvailable(boolean available) {
			this.available = available;
			return this;
		}

		public Builder setReservationId(int reservationId) {
			this.reservationId = reservationId;
			return this;
		}

		public Room build() {
			return new Room(id, roomNumber, roomType, price, available, reservationId);
		}
	}

}