# GitHub Searcher

[![Java](https://img.shields.io/badge/Java-17-blue)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-green)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue)](LICENSE)

A Spring Boot application to **search GitHub repositories** via the GitHub API, store them in a PostgreSQL database, and provide REST endpoints to retrieve repositories with **filtering and sorting**.

## Features

- Search GitHub repositories by query, language, and sort order.
- Store repository data locally for quick retrieval.
- Retrieve stored repositories with optional:
  - Language filter
  - Minimum stars filter
  - Sorting by stars, forks, or last updated
- Clean architecture: **Controller → Service → Repository**.
- Fully unit tested with **JUnit 5** and **Mockito**.

## Technology Stack

- Java 17
- Spring Boot 3
- Spring Data JPA
- PostgreSQL (or any relational database)
- REST APIs with JSON
- JUnit 5 + Mockito for unit tests
- Lombok for boilerplate reduction

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven or Gradle
- PostgreSQL database
- Git

## API Endpoints

| Endpoint | Method | Request Body / Query Parameters | Description | Sample Response |
|----------|--------|--------------------------------|-------------|----------------|
| `/api/github/search` | POST | ```json { "query": "springboot", "language": "java", "sort": "stars" }``` | Search GitHub repositories based on query, language, and sort. Saves results to the database. | ```json { "message": "Repositories fetched and saved successfully", "repositories": [ { "id": 1, "name": "repo1", "language": "java", "stars": 100, "forks": 10, "owner": "owner1", "lastUpdated": "2026-02-11T00:00:00Z" } ] }``` |
| `/api/github/repositories` | GET | Query parameters:<br>`language` (optional) - filter by programming language<br>`minStars` (optional) - minimum star count<br>`sort` (optional: stars, forks, updated; default: stars)` | Retrieve stored repositories with optional filtering and sorting. | ```json [ { "id": 1, "name": "repo1", "language": "java", "stars": 100, "forks": 10, "owner": "owner1", "lastUpdated": "2026-02-11T00:00:00Z" } ]``` |

