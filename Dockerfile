# Use the official OpenJDK base image
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the packaged jar file into the container
COPY target/receiptprocessor.jar /app/receiptprocessor.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the jar file
CMD ["java", "-jar", "receiptprocessor.jar"]
