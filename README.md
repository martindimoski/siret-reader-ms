# siret-reader-ms
This is the Spring Boot project for reading siret data from SIREN API and storing in local csv file.

## Getting Started
The recommended development environment is [IntelliJ IDEA](https://www.jetbrains.com/idea/download/).
Download and install Intellij IDEA (or another IDE of your choice) for your operating system.

The project uses [Docker](https://www.docker.com/) for development.
Follow the getting started instructions provided by Docker to set it up for your operating system.

### Run the application locally with docker
Use docker to run the application in containerized environment:
```
$> docker build --tag=app:latest .
```

```
$> docker run -p8887:8080 app:latest
```

### Run the application locally with maven
```
$> mvn clean package
```

```
$> java -jar target/siret-reader-ms-0.0.1-SNAPSHOT.jar
```
