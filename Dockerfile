FROM openjdk:17-jdk-slim

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src/ ./src
COPY wait-for-it.sh /wait-for-it.sh

RUN chmod +x /wait-for-it.sh

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/backend.challenge-0.0.1-SNAPSHOT.jar"]
