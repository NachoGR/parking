package io.garcia.parking;

import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class ParkingApplication {

	@Autowired
	Commands c;
	public static void main(String[] args) {
		SpringApplication.run(ParkingApplication.class, args);
	}


}
