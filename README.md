What Dockerfile does:

It uses the official OpenJDK 11 JRE slim image as the base image.
Sets the working directory inside the container to /app.
Copies the packaged JAR file receipt-processor.jar from the target directory of your project into the /app directory inside the container.
Exposes port 8080, which is the default port used by Spring Boot applications.
Specifies the command to run the Spring Boot application using the java -jar command.

How to run this project?

To build a Docker image using this Dockerfile, make sure you have Docker installed on your machine, navigate to the directory containing your Dockerfile and your Spring Boot JAR file, and run the following command:

docker build -t receiptprocessor.

This command will build a Docker image named receipt-processor based on the Dockerfile in the current directory.

Once the image is built, you can run the container using the following command:
docker run -p 8080:8080 receiptprocessor


This command will start a container based on the receiptprocessor image, and port 8080 inside the container will be mapped to port 8080 on your host machine, allowing you to access the Spring Boot application running inside the container.



