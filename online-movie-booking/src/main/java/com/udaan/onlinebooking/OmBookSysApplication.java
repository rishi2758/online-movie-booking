package com.udaan.onlinebooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan
public class OmBookSysApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmBookSysApplication.class, args);
	}
}
