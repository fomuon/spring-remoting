package com.naver.spring.remoting.example.provider.booking;

import com.naver.spring.remoting.annotation.RemotingService;
import com.naver.spring.remoting.example.core.booking.Booking;
import com.naver.spring.remoting.example.core.booking.BookingException;
import com.naver.spring.remoting.example.core.booking.CabBookingService;

import static java.lang.Math.random;
import static java.util.UUID.randomUUID;

@RemotingService(value = "cabBookingService")
public class CabBookingServiceImpl implements CabBookingService {

	@Override
	public Booking bookRide(String pickUpLocation) throws BookingException {
		if (random() < 0.3) throw new BookingException("Cab unavailable");
		return new Booking(randomUUID().toString());
	}
}
