package com.spring.entity;

public class Response {
	private boolean success;
	private RoomService roomService;;
	private String message;
	private int code;

	public Response() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Response(boolean success, RoomService roomService, String message, int code) {
		super();
		this.success = success;
		this.roomService = roomService;
		this.message = message;
		this.code = code;
	}

	public boolean isSuccess() {
		return success;
	}

	public RoomService getRoomService() {
		return roomService;
	}

	public String getMessage() {
		return message;
	}

	public int getCode() {
		return code;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public static class Builder {
		private boolean success;
		private RoomService roomService;;
		private String message;
		private int code;

		public Builder setSuccess(boolean success) {
			this.success = success;
			return this;
		}

		public Builder setRoomService(RoomService roomService) {
			this.roomService = roomService;
			return this;
		}

		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		public Builder setCode(int code) {
			this.code = code;
			return this;
		}

		public Response build() {
			return new Response(success, roomService, message, code);
		}
	}
}