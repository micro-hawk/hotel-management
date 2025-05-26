package com.spring.entity;

public class Response {
	
	
	private boolean success = false;
	private Department department = null;
	private String message = null;
	private int code;
	
	public Response(boolean success, Department department, String message, int code) {
		super();
		this.success = success;
		this.department = department;
		this.message = message;
		this.code = code;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	public static class Builder {
		private boolean success = false;
		private Department department = null;
		private String message = null;
		private int code;
		
		public Builder setSuccess(boolean success) {
            this.success = success;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }
        
        public Builder setDepartment(Department department) {
            this.department = department;
            return this;
        }
        
        public Builder setCode(int code) {
            this.code = code;
            return this;
        }

        public Response build() {
            return new Response(success, department, message, code);
        }
		
		
		
	}
	
	
	
}
