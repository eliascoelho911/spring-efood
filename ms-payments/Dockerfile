# Build stage
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Copy gradle wrapper and properties for caching
COPY gradle/ gradle/
COPY gradlew .
COPY gradle.properties .
RUN chmod +x gradlew

# Copy build script and download dependencies (cached layer)
COPY ms-payments/build.gradle.kts .
RUN ./gradlew dependencies --no-daemon

# Copy source and build
COPY ms-payments/src/ src/
RUN ./gradlew build -x test --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Install curl for health checks
RUN apk add --no-cache curl

# Copy built JAR from builder stage
COPY --from=builder /app/build/libs/efood-ms-payments.jar app.jar

EXPOSE 8080
CMD ["sh", "-c", "java -jar app.jar"]