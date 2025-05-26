package com.spring.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class RoomService {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String guestName;
	private String roomNumber;

	@OneToMany(mappedBy = "roomService", cascade = CascadeType.ALL)
	private List<OrderedItem> orderedItems = new ArrayList<>();
	private boolean completed;

	public RoomService() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RoomService(int id, String guestName, String roomNumber, List<OrderedItem> orderedItems, boolean completed) {
		super();
		this.id = id;
		this.guestName = guestName;
		this.roomNumber = roomNumber;
		this.orderedItems = orderedItems;
		this.completed = completed;
	}

	public int getId() {
		return id;
	}

	public String getGuestName() {
		return guestName;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public List<OrderedItem> getOrderedItems() {
		return orderedItems;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public void setOrderedItems(List<OrderedItem> orderedItems) {
		this.orderedItems = orderedItems;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

}