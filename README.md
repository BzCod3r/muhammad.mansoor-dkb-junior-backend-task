# Muhammad Mansoor - DKB Code Factory Junior Backend Developer Task

A production-ready URL shortening service build with:
- **Spring Boot 3**
- **Clean Architecture**
- **Kotlin**
- **JPA/Hibernate**
- **Mockk**

### Getting Started

## 📁 Project Structure (Clean Architecture)

```
src/
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
    docker run --name urlshortener-db -e POSTGRES_DB=urlshortener -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -p 5432:5432 -d postgres
```

### 🚰 Running the App

```bash
    ./gradlew bootRun
```

App will start at: `http://localhost:8080`

## 📌 API Endpoints

### ➕ POST `/api/v1/urls`

Shortens a given long URL.

**Request Body:**

```json
{
  "url": "https://example.com/some/long/url"
}
```

**Response:**

```json
{
  "url": "https://example.com/some/long/url",
  "shortUrl": "abc123"
}
```

### 🔁 POST `/api/v1/resolve`
Resolve  a given code.

**Request Body:**

```json
{
  "url": "abc123"
}
```

**Response:**

```json
{
  "url": "https://example.com/some/long/url",
  "shortUrl": "abc123"
}
```

### 🚰 Running the Tests

```bash
    ./gradlew bootRun
```

