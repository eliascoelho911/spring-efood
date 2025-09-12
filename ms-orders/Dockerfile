# Build stage
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Copy gradle wrapper and dependencies first for caching
COPY gradle/ gradle/
COPY gradlew .
COPY gradle.properties .
COPY ms-orders/build.gradle.kts .
RUN chmod +x gradlew

# Copy source and build
COPY ms-orders/src/ src/
RUN ./gradlew build -x test --no-daemon --max-workers=1

# Runtime stage
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Install curl for health checks
RUN apk add --no-cache curl

# Copy built JAR from builder stage
COPY --from=builder /app/build/libs/efood-ms-orders.jar app.jar

EXPOSE 8080
CMD ["sh", "-c", "java -jar app.jar"]