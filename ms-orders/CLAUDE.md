# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is `ms-orders`, a Spring Boot microservice for managing food delivery orders, part of the efood system. Built with Kotlin, Spring Boot 3.5.5, and PostgreSQL.

## Architecture

- **Language**: Kotlin 1.9.25 with Java 21 toolchain
- **Framework**: Spring Boot 3.5.5 with Spring Data JPA
- **Database**: PostgreSQL (via Docker Compose on port 5433)
- **Build Tool**: Gradle with Kotlin DSL
- **Package Structure**: `com.eliascoelho911.efood.ms_orders`

### Key Components

- **Models**: Domain entities (`Order`, `OrderItem`) with JPA annotations
- **Controllers**: REST endpoints under `/orders` 
- **Services**: Business logic layer (`OrderService`)
- **Repositories**: JPA repositories for data access
- **DTOs**: Request/Response data transfer objects
- **Mappers**: MapStruct-based entity-DTO mapping

### Domain Model

The core `Order` entity includes:
- UUID-based primary key
- Customer CPF identification  
- Order date and status tracking
- Items collection with cascading persistence
- Calculated total value from items
- Status enum: WAITING_FOR_PAYMENT, PAYMENT_QUERY_ERROR, REFUSED, PREPARING, DELIVERY_OUT, DELIVERED

## Development Commands

### Building & Running
- `./gradlew build` - Build the project
- `./gradlew bootRun` - Run the Spring Boot application
- `./gradlew clean` - Clean build directory
- `./gradlew bootJar` - Create executable JAR

### Testing
- `./gradlew test` - Run all tests
- `./gradlew check` - Run all verification tasks

### Database
- `docker-compose up` - Start PostgreSQL container (port 5433)
- Database: `orders_db`, User: `orders_user`

### Configuration
- Main config: `src/main/resources/application.properties`
- JPA configured for DDL auto-update and SQL logging
- Server runs on port 8080