# Hexagonal Architecture — Spring Boot Backend

A backend service built with the **Hexagonal (Ports & Adapters)** architecture pattern, using Spring Boot 3 and PostgreSQL.

---

## Requirements

| Tool        | Version  |
|-------------|----------|
| Java        | 17       |
| Maven       | 3.9+     |
| PostgreSQL  | 16       |
| Docker      | (optional, for containerized setup) |

---

## Database

This project uses **PostgreSQL** as the primary database.

- Default connection: `localhost:5432`
- Default database: `appdb`
- Default credentials: `app / app`
- Schema migrations are managed by **Flyway** (`src/main/resources/db/migration`)

---

## How to Run

### 1. Start PostgreSQL via Docker Compose

```bash
docker-compose up -d postgres
```

### 2. Configure database connection

Add or update `src/main/resources/application-local.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/appdb
spring.datasource.username=app
spring.datasource.password=app
```

### 3. Run the application

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

The server starts on **port 8000**.

### 4. Run tests

```bash
./mvnw clean verify
```

---

## CI/CD

The project includes a **Jenkinsfile** that runs inside a Docker agent (`maven:3.9.9-eclipse-temurin-17`) and executes the following stages:

1. **Check env** — verifies Java and Maven versions
2. **Checkout** — pulls from `master` branch
3. **Unit Test** — runs `mvn clean verify` and collects JUnit reports
4. **SonarQube Analysis** — runs `mvn sonar:sonar` against a local SonarQube instance
5. **Quality Gate** — waits up to 5 minutes for the SonarQube gate result

Start the full CI stack (Jenkins + SonarQube + PostgreSQL):

```bash
docker-compose up -d
```

- Jenkins UI: `http://localhost:8080`
- SonarQube UI: `http://localhost:9000`

---

## Package Structure

```
com.mailong245.hexagonalarchitecture
├── app/
│   ├── config/          # Spring bean configuration (ModelMapper, ObjectMapper, RestTemplate)
│   └── startup/         # Application entry point (main class)
│
├── common/
│   ├── constant/        # Shared constants
│   ├── exception/       # Custom runtime exceptions
│   └── util/            # Shared utility classes
│
├── domain/
│   ├── model/           # Core domain records (Transaction, User) — no framework dependencies
│   ├── port/
│   │   ├── external/    # Outbound port interfaces for third-party services (NotificationService, PaymentService)
│   │   └── persistence/ # Outbound port interfaces for database access (TransactionRepository, UserRepository)
│   └── service/         # Domain logic (pure business rules)
│
├── features/
│   ├── transaction/
│   │   ├── app/         # TransactionService — orchestrates use cases
│   │   ├── client/      # Outbound HTTP clients for the transaction feature
│   │   ├── persistence/ # Feature-scoped repository extensions
│   │   └── web/         # TransactionController and inbound REST request models
│   └── user/
│       ├── app/         # UserService — orchestrates use cases
│       ├── client/      # Outbound HTTP clients for the user feature
│       ├── persistence/ # Feature-scoped repository extensions
│       └── web/         # UserController and inbound REST request models
│
└── infrastructure/
    ├── external/
    │   └── adapter/     # Implementations of external port interfaces (NotificationServiceAdapter, PaymentServiceAdapter)
    ├── observability/   # Logging, metrics, tracing hooks
    └── persistence/
        ├── adapter/
        │   ├── jpa/     # JPA implementations of persistence ports (TransactionRepositoryJpaAdapter, UserRepositoryJpaAdapter)
        │   └── ext/     # Extended/supplemental repository adapters
        ├── entity/      # JPA entities mapped to database tables (TransactionEntity, UserEntity)
        └── repository/  # Spring Data JPA interfaces (TransactionEntityRepository, UserEntityRepository)
```

### Architecture Layers

```
Web (Controller)
      ↓
  Feature App (Service)
      ↓
  Domain Port (Interface)
      ↓
  Infrastructure Adapter (JPA / HTTP)
      ↓
  Database / External Service
```

- **Domain** knows nothing about Spring, JPA, or HTTP — it only defines models and port interfaces.
- **Features** implement use cases by calling domain ports.
- **Infrastructure** provides the concrete implementations of those ports.
- **App** wires everything together via Spring configuration.
