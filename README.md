# siret-reader-ms
This is the Spring Boot project for reading siret data from [SIRENE API](https://entreprise.data.gouv.fr/api_doc/sirene) and storing in local csv file.

## Getting Started
The recommended development environment is [IntelliJ IDEA](https://www.jetbrains.com/idea/download/).
Download and install Intellij IDEA (or another IDE of your choice) for your operating system.

The project uses [Docker](https://www.docker.com/) for development.
Follow the getting started instructions provided by Docker to set it up for your operating system.

### Run the application locally with docker
Use docker to run the application in containerized environment:
```
mvn clean package
```

```
docker build --tag=app:latest .
```

```
docker run -p8887:8080 app:latest
```

### Run the application locally with maven
```
mvn clean package
```

```
java -jar target/siret-reader-ms-0.0.1-SNAPSHOT.jar
```

## Project notes
### Framework
This microservice is developed with the Spring Boot framework. Using this framework means quickly getting started with developing the core of the application without losing time on preparing and configuring it.
Also, the XML configuration is no needed and there is native support for application server - embedded web server.

### Design pattern
For the architecture of this project, the Domain Drive Design (DDD) is chosen. Working with microservices like this one, using DDD is like a standard practice. With it, we are splitting a base code and reducing the complexity.
Right now there is only one domain i the app (establishments) and is every class related only with this domain is located in `.api.establishment` package. Every new domain that will appear in the future, its related classes will be located in `.api.{new_domain_name}` package. Every other class otside the `.api.*` packages is like common class.
One of the purposes using this approach is the easier way to split the monolith application in more microservices in the future if needed.

### Using WebClient for SIRENE API requests instead of RestTemplate
As of Spring Framework 5, alongside the WebFlux stack, Spring introduced a new HTTP client called WebClient.
WebClient is a modern, alternative HTTP client to RestTemplate. Not only does it provide a traditional synchronous API, but it also supports an efficient nonblocking and asynchronous approach.
That said, if we're developing new applications or migrating an old one, it's a good idea to use WebClient. Moving forward, RestTemplate will be deprecated in future versions.

### Using Apache Commons CSV
Writing a CSV file can become complicated when we start thinking about special characters and how to handle them.
Luckily for us, there are many third-party libraries available for working with CSV files, and many of them handle these special characters and other exceptional cases that may occur. One of them is [apache commons CSV](https://commons.apache.org/proper/commons-csv/).

### Using data transfer objects
Using data transfer object maybe makes more sense when there is, for example, relational database and entity classes. But still, it's always better to decouple the model classes and request and response bodies. Sometimes there are more data transfer objects related with one model class, for example one for creating request, one for updating request etc. and it's easier for doing validations and also for swagger documentation.
For mapping data transfer objects into model classes and vise versa, the [mapstruct](https://mapstruct.org/) code generator tool is used.  
