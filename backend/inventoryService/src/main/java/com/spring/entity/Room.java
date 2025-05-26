package com.spring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true, nullable = false)
	private String roomNumber;
	private String roomType;
	private double price;
	private boolean available;
	private String reservationId;

	public Room() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Room(int id, String roomNumber, String roomType, double price, boolean available, String reservationId) {
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

	public String getReservationId() {
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

	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}

}
