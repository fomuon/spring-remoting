package com.naver.spring.remoting.example.core.booking;

import java.io.Serializable;

public class Booking implements Serializable {
	private String bookingCode;

	public Booking(String bookingCode) {
		this.bookingCode = bookingCode;
	}

	public String getBookingCode() {
		return bookingCode;
	}
}
