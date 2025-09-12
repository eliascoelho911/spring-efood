#!/bin/bash

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Project constants
BASE_PACKAGE="com.eliascoelho911.efood"
GROUP_ID="com.eliascoelho911.efood"

print_usage() {
    echo "Usage: ./create-module.sh <module-name> [type]"
    echo ""
    echo "Arguments:"
    echo "  module-name    Name of the module (e.g., ms-products, api-gateway)"
    echo "  type          Module type: 'spring' (default) or 'standard'"
    echo ""
    echo "Module types:"
    echo "  spring     - Spring Boot microservice with JPA (full-featured)"
    echo "  standard   - Basic Spring Boot module (service registry style)"
    echo ""
    echo "Examples:"
    echo "  ./create-module.sh ms-products"
    echo "  ./create-module.sh ms-products spring"
    echo "  ./create-module.sh api-gateway standard"
}

validate_module_name() {
    local module_name="$1"
    
    if [[ ! "$module_name" =~ ^[a-z][a-z0-9]*(-[a-z0-9]+)*$ ]]; then
        echo -e "${RED}Error: Module name must be lowercase, start with a letter, and use hyphens to separate words${NC}"
        echo "Valid examples: ms-products, api-gateway, service-registry"
        exit 1
    fi
    
    if [ -d "$module_name" ]; then
        echo -e "${RED}Error: Module '$module_name' already exists${NC}"
        exit 1
    fi
}

create_directory_structure() {
    local module_name="$1"
    local package_path="${BASE_PACKAGE}.$(echo "$module_name" | tr '-' '_')"
    local package_dir="$(echo "$package_path" | tr '.' '/')"
    
    echo -e "${BLUE}Creating directory structure for $module_name...${NC}" >&2
    
    # Create main directory structure
    mkdir -p "$module_name/src/main/kotlin/$package_dir"
    mkdir -p "$module_name/src/main/resources"
    mkdir -p "$module_name/src/test/kotlin/$package_dir"
    
    echo "$package_path"
}

create_build_gradle() {
    local module_name="$1"
    local module_type="$2"
    local description="$3"
    
    echo -e "${BLUE}Creating build.gradle.kts...${NC}"
    
    if [ "$module_type" = "spring" ]; then
        cat > "$module_name/build.gradle.kts" << EOF
plugins {
	alias(libs.plugins.kotlin.jvm)
	alias(libs.plugins.kotlin.kapt)
	alias(libs.plugins.kotlin.spring)
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.spring.dependency.management)
	alias(libs.plugins.kotlin.jpa)
}

group = "$GROUP_ID"
version = "0.0.1.SNAPSHOT"
description = "$description"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(libs.versions.java.get().toInt())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(libs.spring.boot.starter.data.jpa)
	implementation(libs.spring.boot.starter.validation)
	implementation(libs.spring.boot.starter.web)
	implementation(libs.jackson.module.kotlin)
	implementation(libs.kotlin.reflect)
	implementation(libs.mapstruct)
	kapt(libs.mapstruct.processor)
	implementation(libs.spring.cloud.starter.netflix.eureka.client)
	developmentOnly(libs.spring.boot.devtools)
	developmentOnly(libs.spring.boot.docker.compose)
	runtimeOnly(libs.postgresql)
	testImplementation(libs.spring.boot.starter.test)
	testImplementation(libs.kotlin.test.junit5)
	testRuntimeOnly(libs.junit.platform.launcher)
}

dependencyManagement {
	imports {
		mavenBom(libs.spring.cloud.dependencies.get().toString())
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
EOF
    else
        cat > "$module_name/build.gradle.kts" << EOF
plugins {
	alias(libs.plugins.kotlin.jvm)
	alias(libs.plugins.kotlin.spring)
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.spring.dependency.management)
}

group = "$GROUP_ID"
version = "0.0.1.SNAPSHOT"
description = "$description"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(libs.versions.java.get().toInt())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(libs.kotlin.reflect)
	implementation(libs.spring.cloud.starter.netflix.eureka.server)
	testImplementation(libs.spring.boot.starter.test)
	testImplementation(libs.kotlin.test.junit5)
	testRuntimeOnly(libs.junit.platform.launcher)
}

dependencyManagement {
	imports {
		mavenBom(libs.spring.cloud.dependencies.get().toString())
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
EOF
    fi
}

create_main_application() {
    local module_name="$1"
    local package_path="$2"
    local module_type="$3"
    
    # Convert module name to class name (e.g., ms-orders -> MsOrdersApplication)
    local class_name="$(echo "$module_name" | sed 's/-/ /g' | sed 's/\b\w/\U&/g' | sed 's/ //g')Application"
    
    echo -e "${BLUE}Creating main application class...${NC}"
    
    local src_dir="$module_name/src/main/kotlin/$(echo "$package_path" | tr '.' '/')"
    
    if [ "$module_type" = "spring" ]; then
        cat > "$src_dir/$class_name.kt" << EOF
package $package_path

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class $class_name

fun main(args: Array<String>) {
	runApplication<$class_name>(*args)
}
EOF
    else
        cat > "$src_dir/$class_name.kt" << EOF
package $package_path

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@SpringBootApplication
@EnableEurekaServer
class $class_name

fun main(args: Array<String>) {
	runApplication<$class_name>(*args)
}
EOF
    fi
}

create_test_class() {
    local module_name="$1"
    local package_path="$2"
    
    # Convert module name to test class name (e.g., ms-orders -> MsOrdersApplicationTests)
    local class_name="$(echo "$module_name" | sed 's/-/ /g' | sed 's/\b\w/\U&/g' | sed 's/ //g')ApplicationTests"
    
    echo -e "${BLUE}Creating test class...${NC}"
    
    local test_dir="$module_name/src/test/kotlin/$(echo "$package_path" | tr '.' '/')"
    
    cat > "$test_dir/$class_name.kt" << EOF
package $package_path

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class $class_name {

	@Test
	fun contextLoads() {
	}
}
EOF
}

create_application_properties() {
    local module_name="$1"
    local module_type="$2"
    
    echo -e "${BLUE}Creating application.properties...${NC}"
    
    if [ "$module_type" = "spring" ]; then
        cat > "$module_name/src/main/resources/application.properties" << EOF
spring.application.name=$module_name
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/${module_name//-/_}_db
spring.datasource.username=${module_name//-/_}_user
spring.datasource.password=${module_name//-/_}_password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Eureka Client Configuration
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
eureka.instance.prefer-ip-address=true
EOF
    else
        cat > "$module_name/src/main/resources/application.properties" << EOF
spring.application.name=$module_name
server.port=8761

# Eureka Server Configuration
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
EOF
    fi
}

create_spring_structure() {
    local module_name="$1"
    local package_path="$2"
    
    echo -e "${BLUE}Creating Spring module structure...${NC}"
    
    local src_dir="$module_name/src/main/kotlin/$(echo "$package_path" | tr '.' '/')"
    
    # Create additional directories for Spring modules
    mkdir -p "$src_dir/controller"
    mkdir -p "$src_dir/service"
    mkdir -p "$src_dir/repository"
    mkdir -p "$src_dir/model"
    mkdir -p "$src_dir/dto"
    mkdir -p "$src_dir/mapper"
    
    # Create empty resources directories
    mkdir -p "$module_name/src/main/resources/templates"
    mkdir -p "$module_name/src/main/resources/static"
}

update_settings_gradle() {
    local module_name="$1"
    
    echo -e "${BLUE}Updating settings.gradle.kts...${NC}"
    
    # Check if module is already included
    if grep -q "include(\"$module_name\")" settings.gradle.kts; then
        echo -e "${YELLOW}Module already included in settings.gradle.kts${NC}"
        return
    fi
    
    # Add module to settings.gradle.kts before the enableFeaturePreview line
    sed -i "/enableFeaturePreview/i include(\"$module_name\")" settings.gradle.kts
    
    echo -e "${GREEN}Added $module_name to settings.gradle.kts${NC}"
}

create_claude_md() {
    local module_name="$1"
    local module_type="$2"
    local description="$3"
    
    echo -e "${BLUE}Creating CLAUDE.md documentation...${NC}"
    
    local claude_title="$(echo "$module_name" | tr '-' ' ' | sed 's/.*/\u&/')"
    
    cat > "$module_name/CLAUDE.md" << EOF
# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is \`$module_name\`, a $([ "$module_type" = "spring" ] && echo "Spring Boot microservice" || echo "Spring Boot service") part of the efood system. Built with Kotlin, Spring Boot 3.5.5$([ "$module_type" = "spring" ] && echo ", and PostgreSQL").

## Architecture

- **Language**: Kotlin 1.9.25 with Java 21 toolchain
- **Framework**: Spring Boot 3.5.5$([ "$module_type" = "spring" ] && echo " with Spring Data JPA")
$([ "$module_type" = "spring" ] && echo "- **Database**: PostgreSQL")
- **Build Tool**: Gradle with Kotlin DSL
- **Package Structure**: \`$BASE_PACKAGE.$(echo "$module_name" | tr '-' '_')\`

### Key Components

$([ "$module_type" = "spring" ] && cat << 'SPRING_COMPONENTS'
- **Models**: Domain entities with JPA annotations
- **Controllers**: REST endpoints
- **Services**: Business logic layer
- **Repositories**: JPA repositories for data access
- **DTOs**: Request/Response data transfer objects
- **Mappers**: MapStruct-based entity-DTO mapping
SPRING_COMPONENTS
)

## Development Commands

### Building & Running
- \`./gradlew build\` - Build the project
- \`./gradlew bootRun\` - Run the Spring Boot application
- \`./gradlew clean\` - Clean build directory
- \`./gradlew bootJar\` - Create executable JAR

### Testing
- \`./gradlew test\` - Run all tests
- \`./gradlew check\` - Run all verification tasks

$([ "$module_type" = "spring" ] && cat << 'DATABASE_SECTION'
### Database
- \`docker-compose up\` - Start PostgreSQL container
- Database: \`${module_name//-/_}_db\`, User: \`${module_name//-/_}_user\`

DATABASE_SECTION
)

### Configuration
- Main config: \`src/main/resources/application.properties\`
$([ "$module_type" = "spring" ] && echo "- JPA configured for DDL auto-update and SQL logging")
- Server runs on port $([ "$module_type" = "spring" ] && echo "8080" || echo "8761")
EOF
}

# Main script
main() {
    if [ $# -eq 0 ] || [ "$1" = "-h" ] || [ "$1" = "--help" ]; then
        print_usage
        exit 0
    fi
    
    local module_name="$1"
    local module_type="${2:-spring}"
    
    # Validate inputs
    if [ "$module_type" != "spring" ] && [ "$module_type" != "standard" ]; then
        echo -e "${RED}Error: Invalid module type '$module_type'. Must be 'spring' or 'standard'${NC}"
        exit 1
    fi
    
    validate_module_name "$module_name"
    
    # Generate description
    local description
    if [ "$module_type" = "spring" ]; then
        description="$(echo "$module_name" | tr '-' ' ' | sed 's/.*/\u&/') microservice"
    else
        description="$(echo "$module_name" | tr '-' ' ' | sed 's/.*/\u&/') service"
    fi
    
    echo -e "${GREEN}Creating $module_type module: $module_name${NC}"
    echo -e "${BLUE}Description: $description${NC}"
    echo ""
    
    # Create module
    local package_path
    package_path=$(create_directory_structure "$module_name")
    
    create_build_gradle "$module_name" "$module_type" "$description"
    create_main_application "$module_name" "$package_path" "$module_type"
    create_test_class "$module_name" "$package_path"
    create_application_properties "$module_name" "$module_type"
    create_claude_md "$module_name" "$module_type" "$description"
    
    # Create additional structure for Spring modules
    if [ "$module_type" = "spring" ]; then
        create_spring_structure "$module_name" "$package_path"
    fi
    
    update_settings_gradle "$module_name"
    
    echo ""
    echo -e "${GREEN}✅ Module '$module_name' created successfully!${NC}"
    echo ""
    echo -e "${YELLOW}Next steps:${NC}"
    echo "1. Review and adjust the generated configuration files"
    
    if [ "$module_type" = "spring" ]; then
        echo "2. Configure your database connection in application.properties"
        echo "3. Create your domain models in the model package"
        echo "4. Implement your business logic in controllers and services"
    else
        echo "2. Configure your service-specific properties in application.properties"
        echo "3. Add your service-specific dependencies to build.gradle.kts"
    fi
    
    echo "$([ "$module_type" = "spring" ] && echo "5" || echo "4"). Run './gradlew build' to verify the setup"
    echo ""
    echo -e "${BLUE}Module structure created at: ./$module_name/${NC}"
}

main "$@"