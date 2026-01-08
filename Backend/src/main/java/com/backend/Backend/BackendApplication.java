package com.backend.Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication { // No more "implements CommandLineRunner"

	public static void main(String[] args) {
		// FORCE the correct timezone
		System.setProperty("user.timezone", "Asia/Kolkata");
		SpringApplication.run(BackendApplication.class, args);
	}

	// The 'run' method and 'userRepository' variable should be gone.
}