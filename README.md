🎵 Chinook Music Store API (JDBC Edition)
A high-performance Spring Boot RESTful API managing a digital music store, powered by the classic Chinook dataset. This project is intentionally built without ORM frameworks like JPA/Hibernate to demonstrate mastery over raw SQL and efficient data access using Spring JDBC.

🚀 Key Highlights
Zero JPA/Persistence Dependency: Completely bypasses spring-boot-starter-data-jpa to eliminate Hibernate's overhead, such as complex proxying and "magic" state management.

Pure JDBC (JdbcTemplate): Provides 100% control over SQL execution, allowing for hand-optimized queries and preventing the common "N+1" performance trap.

Lightweight & Fast: Significant reduction in application startup time and memory footprint compared to JPA-based applications.

Chinook Dataset Integration: Implements a real-world business model featuring 11+ interconnected tables (Artists, Albums, Tracks, Invoices, etc.).


🛠 Tech Stack

Language: Java 17+
Framework: Spring Boot 3.x
Data Access: spring-boot-starter-jdbc
Database: SQLServer
