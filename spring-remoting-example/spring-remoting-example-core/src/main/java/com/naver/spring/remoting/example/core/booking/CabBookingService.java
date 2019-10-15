package com.naver.spring.remoting.example.core.booking;

public interface CabBookingService {
	Booking bookRide(String pickUpLocation) throws BookingException;
}
