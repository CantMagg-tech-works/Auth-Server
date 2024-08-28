
FROM maven:3.8.4-openjdk-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package

FROM eclipse-temurin:21

RUN mkdir /opt/app

COPY --from=build /app/target/auth-server-0.0.1-SNAPSHOT.jar /opt/app/auth-server.jar

EXPOSE 9000

CMD ["java", "-jar", "/opt/app/auth-server.jar"]

