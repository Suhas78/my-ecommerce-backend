# Stage 2: Create the final lightweight image using a slim JRE
FROM eclipse-temurin:17
# Set the working directory inside the container
WORKDIR /app

# Copy your compiled .jar file into the container
# Make sure your .jar file is named this in your 'target' folder
COPY target/ecom-project-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that your Spring Boot app runs on
EXPOSE 8081

# The command to run your application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]