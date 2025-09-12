FROM openjdk:21-jdk-slim

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY gradle/ gradle/
COPY gradlew .
COPY build.gradle.kts .
#COPY settings.gradle.kts .

RUN chmod +x gradlew

COPY src/ src/

RUN ./gradlew build -x test

EXPOSE 8081

CMD ["java", "-jar", "build/libs/service-registry-0.0.1-SNAPSHOT.jar"]
