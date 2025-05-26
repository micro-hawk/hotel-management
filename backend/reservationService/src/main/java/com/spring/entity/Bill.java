package com.spring.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Bill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int billNumber;
	private String guestName;
	private String roomNumber;
	private String phoneNumber;
	private Date billDate;
	private double totalAmount;
	private double taxes;
	private double netAmount;
	private boolean checkOutCompleted;
	private boolean paid = false;

	public Bill() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Bill(int billNumber, String guestName, String roomNumber, String phoneNumber, Date billDate,
			double totalAmount, double taxes, double netAmount, boolean checkOutCompleted, boolean paid) {
		super();
		this.billNumber = billNumber;
		this.guestName = guestName;
		this.roomNumber = roomNumber;
		this.phoneNumber = phoneNumber;
		this.billDate = billDate;
		this.totalAmount = totalAmount;
		this.taxes = taxes;
		this.netAmount = netAmount;
		this.checkOutCompleted = checkOutCompleted;
		this.paid = paid;
	}

	public int getBillNumber() {
		return billNumber;
	}

	public String getGuestName() {
		return guestName;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public Date getBillDate() {
		return billDate;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public double getTaxes() {
		return taxes;
	}

	public double getNetAmount() {
		return netAmount;
	}

	public boolean isCheckOutCompleted() {
		return checkOutCompleted;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setBillNumber(int billNumber) {
		this.billNumber = billNumber;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setTaxes(double taxes) {
		this.taxes = taxes;
	}

	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}

	public void setCheckOutCompleted(boolean checkOutCompleted) {
		this.checkOutCompleted = checkOutCompleted;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public static class Builder {
		// TODO Auto-generated method stub
		private int billNumber;
		private String guestName;
		private String roomNumber;
		private String phoneNumber;
		private Date billDate;
		private double totalAmount;
		private double taxes;
		private double netAmount;
		private boolean checkOutCompleted;
		private boolean paid = false;

		public Builder setBillNumber(int billNumber) {
			this.billNumber = billNumber;
			return this;
		}

		public Builder setGuestName(String guestName) {
			this.guestName = guestName;
			return this;
		}

		public Builder setRoomNumber(String roomNumber) {
			this.roomNumber = roomNumber;
			return this;
		}

		public Builder setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}

		public Builder setBillDate(Date billDate) {
			this.billDate = billDate;
			return this;
		}

		public Builder setTotalAmount(double totalAmount) {
			this.totalAmount = totalAmount;
			return this;
		}

		public Builder setTaxes(double taxes) {
			this.taxes = taxes;
			return this;
		}

		public Builder setNetAmount(double netAmount) {
			this.netAmount = netAmount;
			return this;
		}

		public Builder setCheckOutCompleted(boolean checkOutCompleted) {
			this.checkOutCompleted = checkOutCompleted;
			return this;
		}

		public Builder setPaid(boolean paid) {
			this.paid = paid;
			return this;
		}

		public Bill build() {
			return new Bill(billNumber, guestName, roomNumber, phoneNumber, billDate, totalAmount, taxes, netAmount,
					checkOutCompleted, paid);
		}

	}
}
