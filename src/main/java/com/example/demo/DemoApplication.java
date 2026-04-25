package com.example.demo;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		loadEnv();
		SpringApplication.run(DemoApplication.class, args);
	}

	private static void loadEnv() {
		Dotenv dotenv = Dotenv.configure().load();

		String dbUrl = require(dotenv, "DB_URL");
		String dbUsername = require(dotenv, "DB_USERNAME");
		String dbPassword = require(dotenv, "DB_PASSWORD");
		String dbDriver = require(dotenv, "DB_DRIVER");

		System.setProperty("DB_URL", dbUrl);
		System.setProperty("DB_USERNAME", dbUsername);
		System.setProperty("DB_PASSWORD", dbPassword);
		System.setProperty("DB_DRIVER", dbDriver);

		try {
			Class.forName(dbDriver);
		} catch (Exception e) {
			throw new IllegalStateException("Cannot connect to database. Check .env configuration.", e);
		}
	}

	private static String require(Dotenv dotenv, String key) {
		String value = dotenv.get(key);
		if (value == null || value.isBlank()) {
			throw new IllegalStateException("Missing required key in .env: " + key);
		}
		return value;
	}
}
