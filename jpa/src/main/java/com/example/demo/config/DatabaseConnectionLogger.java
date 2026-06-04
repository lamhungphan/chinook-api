package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class DatabaseConnectionLogger implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(DatabaseConnectionLogger.class);

	private final DataSource dataSource;

	public DatabaseConnectionLogger(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void run(String... args) throws Exception {
		try (Connection connection = dataSource.getConnection()) {
			log.info("Connected DB: {}", connection.getMetaData().getURL());
		}
	}
}
