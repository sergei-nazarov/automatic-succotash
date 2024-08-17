FROM maven:3.9.0-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/*.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]

EXPOSE 8080
