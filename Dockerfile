FROM maven:3.9.1-eclipse-temurin-17-alpine AS builder
COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn clean package -DskipTests

FROM amazoncorretto:17-alpine-jdk
WORKDIR "/app"
RUN mkdir "logs"
COPY --from=builder /target/*.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
