# Use an official Java runtime as a parent image
FROM openjdk:17-jre-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY MarketFilter-0.0.1-SNAPSHOT.jar /app/MarketFilter-0.0.1-SNAPSHOT.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "MarketFilter-0.0.1-SNAPSHOT.jar"]
