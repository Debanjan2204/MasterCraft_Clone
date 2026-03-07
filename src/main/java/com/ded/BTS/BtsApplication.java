package com.ded.BTS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class BtsApplication {

	public static void main(String[] args) {
		   Dotenv dotenv = Dotenv.configure()
	                .ignoreIfMissing()  // won't crash if .env is missing
	                .load();

	        // Only set if value exists
	        if (dotenv.get("PGHOST", null) != null) {
	            System.setProperty("PGHOST", dotenv.get("PGHOST"));
	            System.setProperty("PGDATABASE", dotenv.get("PGDATABASE"));
	            System.setProperty("PGUSER", dotenv.get("PGUSER"));
	            System.setProperty("PGPASSWORD", dotenv.get("PGPASSWORD"));
	            System.setProperty("PGSSLMODE", dotenv.get("PGSSLMODE"));
	            System.setProperty("PGCHANNELBINDING", dotenv.get("PGCHANNELBINDING"));
	            System.setProperty("B2_KEY_ID", dotenv.get("B2_KEY_ID"));
	            System.setProperty("B2_APP_KEY", dotenv.get("B2_APP_KEY"));
	            System.setProperty("B2_BUCKET_NAME", dotenv.get("B2_BUCKET_NAME"));
	            System.setProperty("B2_ENDPOINT", dotenv.get("B2_ENDPOINT"));
	        }
		SpringApplication.run(BtsApplication.class, args);
	}

}
