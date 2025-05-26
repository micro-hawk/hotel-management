package com.spring.entity;

import java.util.List;

public class RequestHandler {

	private RoomService roomService;
	private List<OrderedItem> orderedItems;

	public RequestHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RequestHandler(RoomService roomService, List<OrderedItem> orderedItems) {
		super();
		this.roomService = roomService;
		this.orderedItems = orderedItems;
	}

	public RoomService getRoomService() {
		return roomService;
	}

	public List<OrderedItem> getOrderedItems() {
		return orderedItems;
	}

	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}

	public void setOrderedItems(List<OrderedItem> orderedItems) {
		this.orderedItems = orderedItems;
	}

}