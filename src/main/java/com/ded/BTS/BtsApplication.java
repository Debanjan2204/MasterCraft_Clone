package com.ded.BTS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
public class BtsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BtsApplication.class, args);
	}

}
