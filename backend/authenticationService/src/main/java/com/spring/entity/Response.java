package com.spring.entity;
public class Response {
    private boolean success = false;
    private String message = null;
    private Object department = null;
    private Object item = null;
    private Object room = null;
    private Object staff = null;
    private Object roomService = null;
    private Object reservation = null;
    private Object bill = null;
    private String token = null;
    private int code;

    // Constructor
    public Response(boolean success, String message, Object department, Object item, 
                    Object room, Object staff, Object roomService, Object reservation, 
                    Object bill, String token, int code) {
        this.success = success;
        this.message = message;
        this.department = department;
        this.item = item;
        this.room = room;
        this.staff = staff;
        this.roomService = roomService;
        this.reservation = reservation;
        this.bill = bill;
        this.token = token;
        this.code = code;
    }

    // Default Constructor
    public Response() {
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getDepartment() {
        return department;
    }

    public void setDepartment(Object department) {
        this.department = department;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }

    public Object getRoom() {
        return room;
    }

    public void setRoom(Object room) {
        this.room = room;
    }

    public Object getStaff() {
        return staff;
    }

    public void setStaff(Object staff) {
        this.staff = staff;
    }

    public Object getRoomService() {
        return roomService;
    }

    public void setRoomService(Object roomService) {
        this.roomService = roomService;
    }

    public Object getReservation() {
        return reservation;
    }

    public void setReservation(Object reservation) {
        this.reservation = reservation;
    }

    public Object getBill() {
        return bill;
    }

    public void setBill(Object bill) {
        this.bill = bill;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    // toString Method
    @Override
    public String toString() {
        return "Response{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", department=" + department +
                ", item=" + item +
                ", room=" + room +
                ", staff=" + staff +
                ", roomService=" + roomService +
                ", reservation=" + reservation +
                ", bill=" + bill +
                ", token='" + token + '\'' +
                ", code=" + code +
                '}';
    }

    // Builder Pattern (Manually Implemented)
    public static class Builder {
        private boolean success = false;
        private String message = null;
        private Object department = null;
        private Object item = null;
        private Object room = null;
        private Object staff = null;
        private Object roomService = null;
        private Object reservation = null;
        private Object bill = null;
        private String token = null;
        private int code;

        public Builder setSuccess(boolean success) {
            this.success = success;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setDepartment(Object department) {
            this.department = department;
            return this;
        }

        public Builder setItem(Object item) {
            this.item = item;
            return this;
        }

        public Builder setRoom(Object room) {
            this.room = room;
            return this;
        }

        public Builder setStaff(Object staff) {
            this.staff = staff;
            return this;
        }

        public Builder setRoomService(Object roomService) {
            this.roomService = roomService;
            return this;
        }

        public Builder setReservation(Object reservation) {
            this.reservation = reservation;
            return this;
        }

        public Builder setBill(Object bill) {
            this.bill = bill;
            return this;
        }

        public Builder setToken(String token) {
            this.token = token;
            return this;
        }

        public Builder setCode(int code) {
            this.code = code;
            return this;
        }

        public Response build() {
            return new Response(success, message, department, item, room, staff, roomService, reservation, bill, token, code);
        }
    }
}
