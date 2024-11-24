```markdown
# Chat Application

This is a chat application built with **Spring Boot** and various services like **MySQL**, **Redis**, **RabbitMQ**, and **Elasticsearch**. It provides a set of CRUD operations for managing chats and messages. You can interact with the application using the provided Postman collection for easy invocation of the available API endpoints.

---

## Prerequisites

Before you begin, make sure you have the following installed:

- **Docker** (with Docker Compose)
- **Java 17** (if building locally without Docker)
---

## What is Used

- **Spring Boot** for the backend.
- **MySQL** for the database.
- **Redis** for caching.
- **RabbitMQ** for messaging.
- **Elasticsearch** for searching messages.
- **Docker Compose** for managing the multi-container environment.

---

## How to Run the Application

1. Clone the repository to your local machine:

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
