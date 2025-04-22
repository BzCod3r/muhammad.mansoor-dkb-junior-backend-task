# Muhammad Mansoor - DKB Code Factory Junior Backend Developer Task

A production-ready URL shortening service build with:
- **Spring Boot 3**
- **Clean Architecture**
- **Kotlin**
- **JPA/Hibernate**
- **Flyway**
- **Docker-compose**
- **Mockk**

### Getting Started

## 📁 Project Structure (Clean Architecture)

```
src/
├── adapters/            # entry point for the project
├── application/         # Core business logic (entities, usecases)
├── domain/              # Gateways and exception
├── infrastructure/      # REST API-Controllers, JPA, DB config, and persistence implementations

```


### Prerequisites

- Java 21 or higher.
- Maven 3.x or higher.
- Basic understanding Gradle.

### 🐘 PostgreSQL Setup (via Docker)

```bash
    docker run --name urlshortener-db -e POSTGRES_DB=postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres:14
```
### Redis (caching)
```bash
    docker run --name redis -p 6379:6379 -d redis:latest redis-server --requirepass "temppass"
```

---

## 🚀 Spring Profiles

The application supports the following profiles:

- **`dev`** - Local development (default)
- **`test`** - Used for unit/integration testing
- **`production`** - Production-ready configuration

You can specify the active profile using the `SPRING_PROFILES_ACTIVE` environment variable or via Gradle.

### Run with `dev` profile

```bash
    ./gradlew bootRun --args='--spring.profiles.active=dev'
```

### Run with `test` profile

```bash
    ./gradlew bootRun --args='--spring.profiles.active=test'
```

### Run with `prod` profile

```bash
    ./gradlew clean bootJar
    java -jar build/libs/dkb-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

App will start at: `http://localhost:8080`

## 🧪 Running Tests

To run the test suite, use the following command:

```bash
    ./gradlew test
```

## 📚 API Documentation

This Spring Boot project integrates **Swagger UI** for easy API testing and documentation.

### 🔗 Access Swagger UI

Once the application is up and running, you can open Swagger UI in your browser at:
`http://localhost:8080/swagger-ui/index.html`


## Actuator Endpoints

- **Health**: `/actuator/health` (GET) – Check the health status of the application.
- **Info**: `/actuator/info` (GET) – Retrieve application metadata (e.g., version, build).
- **Metrics**: `/actuator/metrics` (GET) – Access various metrics such as memory usage and request counts.
- **Environment**: `/actuator/env` (GET) – Retrieve environment properties and configuration details.
- **Loggers**: `/actuator/loggers` (GET) – View and configure logging levels for the application.

## Docker Compose File

The project includes a `docker-compose.yml` file that can be used to set up the PostgreSQL database and run the Spring Boot application in production mode automatically.

To start the PostgreSQL container and run the Spring Boot application in production mode, use the following command:

```bash
    docker-compose up --build
```
