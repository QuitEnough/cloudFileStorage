FROM maven:3 as build
WORKDIR /build
COPY src src
COPY pom.xml pom.xml
RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests

FROM openjdk:17
VOLUME /tmp
WORKDIR /app
COPY --from=build /build/target/cloudFileStorage-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8081