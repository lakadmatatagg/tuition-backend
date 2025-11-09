# Tuition Backend (Spring Boot)

A standalone backend service built with **Spring Boot** for managing system data, authentication, and user operations.

This project serves as the server-side layer for internal administrative tasks.

## 🚀 Tech Stack

- **Framework:** Spring Boot
- **Language:** Java 21
- **Build Tool:** Maven
- **Database:** (To be connected separately)

## ⚙️ Getting Started

### Prerequisites
- Java 21+
- Maven 3.6+
- (Optional) Docker (for database)

### Installation

```bash
# Clone the repo
git clone https://github.com/lakadmatatagg/tuition-backend.git
cd tuition-backend

# Build the project
./build.bat

# Run the project
cd web-313
./run.bat
```

The backend will be available at `http://localhost:8080`.

## 📦 Environment Variables

Environment-specific settings can be managed inside:
- `web-313/src/main/resources/application.yml`
- You can add custom profiles like `application-dev.yml` if needed.

Key settings include:
- Database connection URLs
- Keycloak configurations (if integrated later)

## 🗂️ Project Structure

```bash
tuition-backend/
├── common-313/
├── mapper-313/
├── repository-313/
├── service-313/
├── web-313/
├── pom.xml
└── README.md

```

### Explanation

- **`common-313/`** – Location for Entities and Models
- **`mapper-313/`** – MapStruct mappers to convert between entities and DTOs.
- **`repository-313/`** – Spring Data JPA repositories for database access.
- **`service-313/`** – Business logic layer.
- **`web-313/`** – REST controllers for handling API requests.
- **`resources/`** – Static files and application configuration files.
- **`pom.xml`** – Maven project descriptor. Notes: Every other modules also has independent pom.xml

## 🛠️ Available Commands

| Command                  | Description               |
|---------------------------|----------------------------|
| `./run.bat`  | Start the backend server (web-313 dir)  |
| `./build.bat`    | Build the project (root dir)         |
