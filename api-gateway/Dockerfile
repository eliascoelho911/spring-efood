FROM openjdk:21-jdk-slim

WORKDIR /app

COPY gradle/ gradle/
COPY gradlew .
COPY api-gateway/build.gradle.kts .
RUN chmod +x gradlew

COPY api-gateway/src/ src/

RUN ./gradlew build -x test

EXPOSE 8080

CMD ["java", "-jar", "build/libs/efood-api-gateway.jar"]