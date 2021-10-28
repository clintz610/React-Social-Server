package com.revature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.revature.models"}) 
public class ReverbApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReverbApplication.class, args);
	}

}
