# ===== Stage 1: Build the app =====
FROM eclipse-temurin:17-jdk AS build

# Set work directory
WORKDIR /app

# Install Maven manually
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Copy project files
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src

# Build the JAR
RUN mvn clean package -DskipTests

# ===== Stage 2: Run the app =====
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy built JAR from previous stage
COPY --from=build /calculator/target/*.jar app.jar
COPY .env .env

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

