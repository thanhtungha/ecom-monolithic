# Use an official OpenJDK runtime as a base image
FROM amazoncorretto:17

# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR file into the container at the specified working directory
COPY target/monolithic-1.0.jar /app

# Specify the command to run Spring Boot application when the container starts
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=product", "monolithic-1.0.jar"]
