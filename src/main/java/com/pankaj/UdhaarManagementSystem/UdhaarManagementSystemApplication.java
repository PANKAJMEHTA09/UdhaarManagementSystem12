package com.pankaj.UdhaarManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class UdhaarManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(UdhaarManagementSystemApplication.class, args);
	}

}
