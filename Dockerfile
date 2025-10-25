# Stage 1: Build the application using Maven with JDK 17
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final lightweight image using a slim JRE
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copy the built JAR file from the 'build' stage
COPY --from=build /app/target/*.jar app.jar
# Expose the port the application runs on (Make sure this matches your server.port)
EXPOSE 8081
# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]