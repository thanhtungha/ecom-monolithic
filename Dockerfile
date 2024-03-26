# Use a Maven image as a base image
FROM maven:3.9.6-amazoncorretto-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project files to the container
COPY pom.xml .
COPY src ./src

# Build the Maven project
ENTRYPOINT ["mvn", "clean", "package"]

# Use an official OpenJDK runtime as a base image
FROM amazoncorretto:17

# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR file into the container at the specified working directory
COPY target/monolithic-1.0-Dev.jar /app

# Specify the command to run Spring Boot application when the container starts
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=local", "monolithic-1.0-Dev.jar"]
