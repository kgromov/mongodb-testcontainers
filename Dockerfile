FROM maven:3.8.5-openjdk-17
#FROM eclipse-temurin:24-jdk-alpine

WORKDIR /app
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run