# Chat Application

This is a chat application built with **Spring Boot**. It integrates multiple services such as **MySQL**, **Redis**, **RabbitMQ**, and **Elasticsearch** to manage and support scalable chat and messaging operations. The system allows users to create and manage chats and messages, as well as perform searches within messages using Elasticsearch. Asynchronous operations ensure scalability and efficiency for managing data persistence and indexing.

---

## Prerequisites

Before running the application locally, ensure you have the following installed:

- **Docker** (with Docker Compose) to run the multi-container setup.
- **Java 17** (if building the application locally without Docker).
- **Maven** (or any Java build tool you prefer) for building the Spring Boot application.

---

## Technologies Used

- **Spring Boot** for the backend logic.
- **MySQL** as the relational database for persistent data storage.
- **Redis** for caching and atomic ID generation.
- **RabbitMQ** for managing asynchronous messaging tasks like chat/message persistence and indexing.
- **Elasticsearch** for fast message searching.
- **Docker Compose** to set up and manage a multi-container environment for all services.

---

## How to Run the Application

1. **Clone the repository** to your local machine:

   ```bash
   git clone <your-repository-url>
   cd backend.challenge
   ```

2. Build and run the application with Docker Compose:

   ```bash
   docker compose -f docker-compose.yml up --build -d
   ```

   This command will build the Docker images and start all the services (backend, MySQL, Redis, RabbitMQ, Elasticsearch) in detached mode.

---

## Postman Collection

To easily interact with the available CRUD operations, you can import the provided Postman collection. The collection contains pre-configured API requests for managing chats, messages, and other resources.

- Import this file into Postman and use it to test and invoke the endpoints.

---

## Accessing the Application

Once the application is running, you can access the following:

- **Backend API**: `http://localhost:8080`
- **Elasticsearch**: `http://localhost:9200`

---

## Stopping the Services

To stop and remove all running containers and volumes, use:

```bash
docker compose down --volumes
```
---
