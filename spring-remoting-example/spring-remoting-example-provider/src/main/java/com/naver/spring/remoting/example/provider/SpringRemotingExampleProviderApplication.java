package com.naver.spring.remoting.example.provider;

import com.naver.spring.remoting.example.core.Booking;
import com.naver.spring.remoting.example.core.BookingException;
import com.naver.spring.remoting.example.core.CabBookingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SpringRemotingExampleProviderApplication {
	private final CabBookingService cabBookingService;

	public SpringRemotingExampleProviderApplication(CabBookingService cabBookingService) {
		this.cabBookingService = cabBookingService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringRemotingExampleProviderApplication.class, args);
	}

	@GetMapping("/cabBooking")
	public Booking getCabBooking() throws BookingException {
		return cabBookingService.bookRide("");
	}
}
