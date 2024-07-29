# Use the official Maven image to build the app
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Use the official OpenJDK 17 image to run the app
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/MarketFilter-0.0.1-SNAPSHOT.jar /app/MarketFilter-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app/MarketFilter-0.0.1-SNAPSHOT.jar"]
