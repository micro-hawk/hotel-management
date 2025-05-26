package com.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderedItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "room_service_id", referencedColumnName = "id")
	@JsonIgnore
	private RoomService roomService; // for which room order is placed
	private String name;
	private String description;
	private int quantity;
	private double price;

	public OrderedItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderedItem(int id, RoomService roomService, String name, String description, int quantity, double price) {
		super();
		this.id = id;
		this.roomService = roomService;
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public RoomService getRoomService() {
		return roomService;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
