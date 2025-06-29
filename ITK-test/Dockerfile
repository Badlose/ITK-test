FROM openjdk:17-jdk-slim
WORKDIR /app
COPY src/main/resources/liquibase ./liquibase
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]