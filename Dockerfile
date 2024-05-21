# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the executable JAR file into the container
COPY target/rest-0.0.1-SNAPSHOT.jar /app/rest-0.0.1-SNAPSHOT.jar

# Make port 8080 available to the world outside this container
EXPOSE 8181

# Run the JAR file
ENTRYPOINT ["java", "-jar", "rest-0.0.1-SNAPSHOT.jar"]