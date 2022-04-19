FROM openjdk:17-slim-buster
VOLUME /tmp
EXPOSE 8080
COPY target/siret-reader-ms-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]