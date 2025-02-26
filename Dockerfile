# Use an official OpenJDK runtime as a base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file from the Maven build into the container
COPY target/HibernateToDo-1.0-SNAPSHOT.jar /app/app.jar

# Run the application (without exposing a port)
CMD ["java", "-jar", "/app/app.jar"]