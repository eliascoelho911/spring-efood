# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is `ms-users`, a Spring Boot microservice part of the efood system. Built with Kotlin, Spring Boot 3.5.5, and PostgreSQL.

## Architecture

- **Language**: Kotlin 1.9.25 with Java 21 toolchain
- **Framework**: Spring Boot 3.5.5 with Spring Data JPA
- **Database**: PostgreSQL
- **Build Tool**: Gradle with Kotlin DSL
- **Package Structure**: `com.eliascoelho911.efood.ms_users`

### Key Components

- **Models**: Domain entities with JPA annotations
- **Controllers**: REST endpoints
- **Services**: Business logic layer
- **Repositories**: JPA repositories for data access
- **DTOs**: Request/Response data transfer objects
- **Mappers**: MapStruct-based entity-DTO mapping

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
- \`docker-compose up\` - Start PostgreSQL container
- Database: \`${module_name//-/_}_db\`, User: \`${module_name//-/_}_user\`

### Configuration
- Main config: `src/main/resources/application.properties`
- JPA configured for DDL auto-update and SQL logging
- Server runs on port 8080
