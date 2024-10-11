FROM maven:3 as build
WORKDIR /build
COPY src src
COPY pom.xml pom.xml

FROM openjdk:17
VOLUME /tmp
ARG JAR_FILE=cloudFileStorage-0.0.1-SNAPSHOT.jar
WORKDIR /app
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]