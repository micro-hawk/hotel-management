package com.spring.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int numberOfChildren;
	private int numberOfAdults;
	private Date checkInDate;
	private Date checkOutDate;
	private int numberOfNights;
	private String roomNumber;
	private String roomType;
	private boolean billed = false;
	@OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
	private List<Guest> guests = new ArrayList<>();

	public Reservation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Reservation(int id, int numberOfChildren, int numberOfAdults, Date checkInDate, Date checkOutDate,
			int numberOfNights, String roomNumber, String roomType, boolean billed, List<Guest> guests) {
		super();
		this.id = id;
		this.numberOfChildren = numberOfChildren;
		this.numberOfAdults = numberOfAdults;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.numberOfNights = numberOfNights;
		this.roomNumber = roomNumber;
		this.roomType = roomType;
		this.billed = billed;
		this.guests = guests;
	}

	public int getId() {
		return id;
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public int getNumberOfAdults() {
		return numberOfAdults;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public int getNumberOfNights() {
		return numberOfNights;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public String getRoomType() {
		return roomType;
	}

	public boolean isBilled() {
		return billed;
	}

	public List<Guest> getGuests() {
		return guests;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

	public void setNumberOfAdults(int numberOfAdults) {
		this.numberOfAdults = numberOfAdults;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public void setNumberOfNights(int numberOfNights) {
		this.numberOfNights = numberOfNights;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public void setBilled(boolean billed) {
		this.billed = billed;
	}

	public void setGuests(List<Guest> guests) {
		this.guests = guests;
	}

}