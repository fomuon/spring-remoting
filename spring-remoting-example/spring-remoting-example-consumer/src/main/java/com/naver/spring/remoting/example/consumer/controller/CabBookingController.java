package com.naver.spring.remoting.example.consumer.controller;

import com.naver.spring.remoting.example.core.booking.Booking;
import com.naver.spring.remoting.example.core.booking.BookingException;
import com.naver.spring.remoting.example.core.booking.CabBookingService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@RestController
public class CabBookingController {

	private final CabBookingService cabBookingService;

	public CabBookingController(CabBookingService cabBookingService) {
		this.cabBookingService = cabBookingService;
	}

	@GetMapping("/cabBooking")
	public Booking bookCap() throws BookingException {
		return cabBookingService.bookRide("naver");
	}

	@ExceptionHandler(BookingException.class)
	public String handleBookingException(BookingException ex) {
		return "Error : " + ex.getMessage();
	}
}
