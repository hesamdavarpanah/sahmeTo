## ShameTo, The Lottery Site with Spring Boot and Docker Compose

![site-image](./sahmeTo.png)

This project implements a lottery site using Spring Boot and can be containerized with Docker Compose.

### Features

* **Authentication:** JWT based authentication for secure user access.
* **OTP Validation:** Two-factor authentication for added security.
* **Resilience Rate Limiting:** Prevents abuse through API rate limiting.
* **CRUD Operations:** Create, Read, Update, and Delete functionalities for categories and products.

### Prerequisites

* Java 20+
* Maven
* Docker Engine
* Docker Compose

### Installation

1. Clone the repository:

```bash
git clone https://github.com/your-username/lottery-site.git
```

2. Navigate to the project directory:

```bash
cd sahmeTo
```

3. Build the project:

```bash
mvn package
```

4. (Optional) Build the Docker image:

```bash
mvn docker:build
```

### Running with Docker Compose

1. (Optional) Create a file named `docker-compose.yml` with the following content:

```yaml
services:
  sahmeto:
    build: .
    ports:
      - 8080:8080
  database:
    image: postgres:latest
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: sahmeto_db
    volumes:
      - postgres_data:/var/lib/postgresql/data
  nosql-database:
    image: redis:latest
    privileged: true
    environment:
      REDIS_PASSWORD: redis
    command:
      - /bin/sh
      - -c
      - redis-server --requirepass "$${REDIS_PASSWORD:?REDIS_PASSWORD variable is not set}"
    ports:
      - "6379:6379"
    

volumes:
  postgres_data:
```

**Note:**

*  Replace `postgres` and `redis` password with a strong password.
*  Adjust the ports if necessary.

2. Run the application:

```bash
docker compose up -d
```

This will start two containers:

* `sahmeto`: Your Spring Boot application.
* `database`: A PostgreSQL database container.
* `nosql-database`: A Redis in cache database container.

### Accessing the Application

Once the containers are running, the application will be accessible at http://localhost:8080.

**Please note:** This is a basic setup, and you'll need to implement the specific functionalities like user registration, login, JWT generation, OTP handling, rate limiting configuration, and CRUD operations for categories and products within the Spring Boot application.

### License

This project is licensed under the [MIT License](./LICENSE).

### Additional Notes

* This README provides a starting point. You'll need to implement the actual functionalities in the Spring Boot application code.
* Configuration files like application.properties and security configuration are not included here for brevity. You'll need to create these files and configure them appropriately.
* Consider using a secure database connection pool in production environments.
* Remember to replace the placeholder password for the database container.

### Contributing

Feel free to fork this repository and contribute your improvements.
