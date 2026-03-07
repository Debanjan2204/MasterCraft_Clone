package com.ded.BTS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class BtsApplication {

	public static void main(String[] args) {
		 Dotenv dotenv = Dotenv.load();
	        System.setProperty("PGHOST", dotenv.get("PGHOST"));
	        System.setProperty("PGDATABASE", dotenv.get("PGDATABASE"));
	        System.setProperty("PGUSER", dotenv.get("PGUSER"));
	        System.setProperty("PGPASSWORD", dotenv.get("PGPASSWORD"));
	        System.setProperty("PGSSLMODE", dotenv.get("PGSSLMODE"));
		SpringApplication.run(BtsApplication.class, args);
	}

}
