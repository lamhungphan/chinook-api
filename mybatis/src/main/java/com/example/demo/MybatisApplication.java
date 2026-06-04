package com.example.demo;

import io.github.cdimascio.dotenv.Dotenv;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.demo.dao")
public class MybatisApplication {

    public static void main(String[] args) {
        loadEnv();
        SpringApplication.run(MybatisApplication.class, args);
    }

    private static void loadEnv() {
        try {
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

            String dbUrl = getEnvValue(dotenv, "DB_URL", "jdbc:sqlserver://localhost:1433;databaseName=Chinook;trustServerCertificate=true");
            String dbUsername = getEnvValue(dotenv, "DB_USERNAME", "sa");
            String dbPassword = getEnvValue(dotenv, "DB_PASSWORD", "");
            String dbDriver = getEnvValue(dotenv, "DB_DRIVER", "com.microsoft.sqlserver.jdbc.SQLServerDriver");

            System.setProperty("spring.datasource.url", dbUrl);
            System.setProperty("spring.datasource.username", dbUsername);
            System.setProperty("spring.datasource.password", dbPassword);
            System.setProperty("spring.datasource.driver-class-name", dbDriver);

            try {
                Class.forName(dbDriver);
            } catch (ClassNotFoundException e) {
                System.err.println("Cannot load database driver. Check .env configuration.");
            }
        } catch (Exception e) {
            System.err.println("Error loading environment: " + e.getMessage());
        }
    }

    private static String getEnvValue(Dotenv dotenv, String key, String defaultValue) {
        String value = dotenv.get(key);
        return (value != null && !value.isBlank()) ? value : defaultValue;
    }
}
