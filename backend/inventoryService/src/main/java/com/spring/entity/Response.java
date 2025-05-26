package com.spring.entity;

public class Response {
	private boolean success = false;
	private String message = null;
	private Item item = null;
	private Room room = null;
	private Staff staff = null;
	private int code;

	public Response() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Response(boolean success, String message, Item item, Room room, Staff staff, int code) {
		super();
		this.success = success;
		this.message = message;
		this.item = item;
		this.room = room;
		this.staff = staff;
		this.code = code;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}

	public Item getItem() {
		return item;
	}

	public Room getRoom() {
		return room;
	}

	public Staff getStaff() {
		return staff;
	}

	public int getCode() {
		return code;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public void setCode(int code) {
		this.code = code;
	}

	// Builder Pattern (Manually Implemented)
	public static class Builder {
		private boolean success = false;
		private String message = null;
		private Item item = null;
		private Room room = null;
		private Staff staff = null;
		private int code;

		public Builder setSuccess(boolean success) {
			this.success = success;
			return this;
		}

		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		public Builder setItem(Item item) {
			this.item = item;
			return this;
		}

		public Builder setRoom(Room room) {
			this.room = room;
			return this;
		}

		public Builder setStaff(Staff staff) {
			this.staff = staff;
			return this;
		}

		public Builder setCode(int code) {
			this.code = code;
			return this;
		}

		public Response build() {
			return new Response(success, message, item, room, staff, code);
		}
	}
}
