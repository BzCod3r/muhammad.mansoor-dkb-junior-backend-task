# Stage 1: Build the application with JDK 21 and Gradle 8.5
FROM gradle:8.5-jdk21 as builder

# Set the working directory inside the container
WORKDIR /app

# Copy the necessary build files (this rarely changes)
COPY build.gradle.kts settings.gradle.kts ./

# Copy the source code into the container
COPY src ./src

# Build the application with the 'prod' profile (this will generate the .jar file)
RUN gradle clean build -Pprofile=prod --no-daemon

# Stage 2: Run the application
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file from the builder stage (replace with your actual jar name)
COPY --from=builder /app/build/libs/dkb-0.0.1-SNAPSHOT.jar app.jar

# Set the 'prod' profile for the Spring Boot app
ENV SPRING_PROFILES_ACTIVE=prod

# Expose the port your Spring Boot app uses
EXPOSE 8080

# Command to run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
