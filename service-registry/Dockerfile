# Build stage
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Copy gradle wrapper and dependencies first for caching
COPY gradle/ gradle/
COPY gradlew .
COPY gradle.properties .
COPY service-registry/build.gradle.kts .
RUN chmod +x gradlew

# Copy source and build
COPY service-registry/src/ src/
RUN ./gradlew build -x test --no-daemon --max-workers=1

# Runtime stage
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Install curl for health checks
RUN apk add --no-cache curl

# Copy built JAR from builder stage
COPY --from=builder /app/build/libs/efood-service-registry.jar app.jar

EXPOSE 8081

CMD ["sh", "-c", "java -jar app.jar"]
