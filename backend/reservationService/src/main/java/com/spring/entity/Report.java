package com.spring.entity;

public class Report {

	private long reservations;
	private double revenue;

	public Report() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Report(long reservations, double revenue) {
		super();
		this.reservations = reservations;
		this.revenue = revenue;
	}

	public long getReservations() {
		return reservations;
	}

	public double getRevenue() {
		return revenue;
	}

	public void setReservations(long reservations) {
		this.reservations = reservations;
	}

	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}

}