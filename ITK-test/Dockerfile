FROM maven:3.9.10-eclipse-temurin-17
WORKDIR /app
COPY pom.xml .
COPY ./src ./src
COPY src/main/resources/liquibase ./liquibase
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
ENV APP_CONFIG_DIR=/app/config
ENV DATABASE_URL=jdbc:postgresql://localhost:5432/ITK-test
ENV DATABASE_USERNAME=postgres
ENV DATABASE_PASSWORD=postgres

WORKDIR /app
COPY  --from=builder /app/target/*.jar app.jar
EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=10s --retries=3 CMD curl -f http://localhost:8080/actuator/health || exit 1
ENTRYPOINT ["java", "-jar", "app.jar"]