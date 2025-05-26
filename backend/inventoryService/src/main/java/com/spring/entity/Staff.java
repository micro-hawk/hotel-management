package com.spring.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Staff {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String email;
	private String position;
	private int departmentId;
	private String contactNumber;
	private String workDescription;

	public Staff() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Staff(int id, String name, String email, String position, int departmentId, String contactNumber,
			String workDescription) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.position = position;
		this.departmentId = departmentId;
		this.contactNumber = contactNumber;
		this.workDescription = workDescription;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPosition() {
		return position;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public String getWorkDescription() {
		return workDescription;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public void setWorkDescription(String workDescription) {
		this.workDescription = workDescription;
	}

}
