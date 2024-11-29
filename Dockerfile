# Use OpenJDK as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built .jar file from the Maven target directory into the Docker image
COPY target/myapp.jar /app/myapp.jar

# Expose the port the app will run on (replace 8080 with your app’s port)
EXPOSE 9787

# Command to run the Java application
CMD ["java", "-jar", "myapp.jar"]
