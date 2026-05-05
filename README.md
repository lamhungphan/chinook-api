# Chinook Music Store API

A Spring Boot REST API backed by the classic **Chinook** sample database (artists, albums, tracks, invoices, and related tables). This repository compares **four ways** to move data between SQL Server and the application layer.

---

## Branches and technologies

Each branch shows a different data-access style

### `jdbc`

- **Technology:** Spring JDBC (`JdbcTemplate`)
- **Focus:** Plain SQL and row mappers; full control over queries and results; no ORM. Useful for seeing exactly what runs and avoiding hidden lazy-loading behaviour.

### `jpa`

- **Technology:** Jakarta Persistence / Hibernate
- **Focus:** Entity mappings (`@Entity`, associations), Spring Data JPA repositories, declarative persistence. Suited when you prefer object-centric modelling and standard CRUD abstractions.

### `my-batis`

- **Technology:** MyBatis
- **Focus:** SQL-first mapping (XML or annotations) with explicit statements; MyBatis binds parameters and maps rows. Sits between raw JDBC and a full ORM.

### `main`

- **Technology:** Combined showcase
- **Focus:** Ties the approaches together or mixes patterns so you can compare workflows in one checkout.

Clone the branch you need and follow its layout (packages may differ slightly per stack).

---

## Tech stack

- **Language:** Java 17+
- **Framework:** Spring Boot 3.x
- **Database:** Microsoft SQL Server

Dependency variants by branch: `spring-boot-starter-jdbc`, `spring-boot-starter-data-jpa`, or MyBatis Spring Boot starter.

---

## Purpose

This repo exists to **compare data-access styles**—from hand-written JDBC through MyBatis to JPA—using the same domain idea (Chinook) so differences in SQL visibility, boilerplate, and runtime behaviour are easier to reason about.