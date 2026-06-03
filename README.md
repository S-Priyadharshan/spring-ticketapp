# Event Ticket Platform

A RESTful backend for managing events, ticket purchasing, QR code generation, and ticket validation. Built with Spring Boot 4 and secured via Keycloak (OAuth2/JWT).

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.0.5 |
| Security | Spring Security + OAuth2 Resource Server (Keycloak JWT) |
| Persistence | Spring Data JPA + PostgreSQL |
| Mapping | MapStruct 1.6.3 |
| QR Codes | ZXing 3.5.1 |
| Boilerplate | Lombok 1.18.36 |
| Build | Maven |

---

## Architecture

The project follows a standard layered architecture:

```
Controller  →  Service (interface + impl)  →  Repository  →  Database
                    ↕
                 Mapper (MapStruct)
                    ↕
                DTO / Entity
```

- **Controllers** handle HTTP, parse the JWT principal, and delegate to services
- **Services** contain all business logic; interfaces and `impl` classes are separated
- **Repositories** extend `JpaRepository` with custom queries where needed
- **Mappers** use MapStruct to convert between entities, internal DTOs, and API DTOs
- **Filters** include a `UserProvisioningFilter` that auto-creates a local `User` record on first authenticated request, syncing from Keycloak claims

---

## Domain Model

```
User ──< Event (organizer)
User >──< Event (attendees, staff — join tables)
Event ──< TicketType ──< Ticket ──< TicketValidation
                                └──< QrCode
```

| Entity | Key Fields |
|---|---|
| `User` | `id` (Keycloak UUID), `name`, `email` |
| `Event` | `name`, `venue`, `startDate`, `endDate`, `salesStart/End`, `status` |
| `TicketType` | `name`, `price`, `description`, `totalAvailable` |
| `Ticket` | `status` (PURCHASED / CANCELLED), owner, ticketType |
| `QrCode` | `value` (base64 PNG), `status` (ACTIVE / EXPIRED) |
| `TicketValidation` | `status` (VALID / INVALID / EXPIRED), `validationMethod` (QR_SCAN / MANUAL) |

---

## API Endpoints

### Public (no auth required)

| Method | Path | Description |
|---|---|---|
| `GET` | `/api/v1/published-events` | List published events (supports `?q=` full-text search) |
| `GET` | `/api/v1/published-events/{eventId}` | Get details of a published event |

### Authenticated (any role)

| Method | Path | Description |
|---|---|---|
| `GET` | `/api/v1/tickets` | List tickets owned by the current user |
| `GET` | `/api/v1/tickets/{ticketId}` | Get ticket details |
| `GET` | `/api/v1/tickets/{ticketId}/qr-codes` | Download ticket QR code as PNG |
| `POST` | `/api/v1/events/{eventId}/ticket-types/{ticketTypeId}/tickets` | Purchase a ticket |

### Organizer only (`ROLE_ORGANIZER`)

| Method | Path | Description |
|---|---|---|
| `POST` | `/api/v1/events` | Create an event with ticket types |
| `GET` | `/api/v1/events` | List organizer's own events |
| `GET` | `/api/v1/events/{eventId}` | Get own event details |
| `PUT` | `/api/v1/events/{eventId}` | Update event and ticket types |
| `DELETE` | `/api/v1/events/{eventId}` | Delete an event |

### Staff only (`ROLE_STAFF`)

| Method | Path | Description |
|---|---|---|
| `POST` | `/api/v1/ticket-validations` | Validate a ticket (QR scan or manual) |

---

## Key Design Decisions

**Pessimistic locking on ticket purchase** — `TicketTypeRepository.findByIdWithLock` uses `PESSIMISTIC_WRITE` to prevent overselling under concurrent requests.

**Full-text search** — `EventRepository.searchEvents` uses PostgreSQL's `to_tsvector` / `plainto_tsquery` for efficient keyword search across event name and venue.

**User provisioning filter** — `UserProvisioningFilter` runs after `BearerTokenAuthenticationFilter`. On every authenticated request it upserts the user into the local DB using the Keycloak `sub` claim as the primary key, so no separate registration flow is needed.

**Duplicate ticket validation guard** — `TicketValidationServiceImpl` checks existing validations; if a `VALID` record already exists for a ticket, any subsequent scan returns `INVALID` rather than re-validating.

**QR code storage** — QR codes are generated as 300×300 PNG images via ZXing, base64-encoded, and stored in the `qr_codes` table. The `GET /qr-codes` endpoint decodes and returns raw bytes with `image/png` content type.

---

## Event Lifecycle

```
DRAFT  →  PUBLISHED  →  COMPLETED
           ↓
        CANCELLED
```

Only `PUBLISHED` events appear in the public endpoints. Ticket sales respect the optional `salesStartDate` / `salesEndDate` window.

---

## Prerequisites

- Java 21
- Maven 3.9+
- PostgreSQL (running on port 5432)
- Keycloak (running on port 9090, realm: `event-ticket-platform`)

---

## Configuration

Set the following before running:

```properties
# application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=${POSTGRES_PASSWORD}   # set as env variable

spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:9090/realms/event-ticket-platform
```

Export the password before running:

```bash
# Windows
set POSTGRES_PASSWORD=yourpassword

# Linux / macOS
export POSTGRES_PASSWORD=yourpassword
```

---

## Running the Application

```bash
git clone https://github.com/S-Priyadharshan/spring-ticketapp.git
cd spring-ticketapp
mvn spring-boot:run
```

The server starts on `http://localhost:8080` by default.

---

## Keycloak Setup

1. Create a realm named `event-ticket-platform`
2. Create a client for the backend (resource server — bearer-only)
3. Define realm roles: `ROLE_ORGANIZER`, `ROLE_STAFF`
4. Assign roles to users as needed

The `JwtAuthenticationConverter` reads roles from the `realm_access.roles` claim and filters for entries prefixed with `ROLE_`.

---

## Exception Handling

All exceptions are handled centrally in `GlobalExceptionHandler` (`@RestControllerAdvice`):

| Exception | HTTP Status |
|---|---|
| `UserNotFoundException` | 400 Bad Request |
| `EventNotFoundException` | 400 Bad Request |
| `EventUpdateException` | 400 Bad Request |
| `TicketTypeNotFoundException` | 400 Bad Request |
| `TicketSoldOutException` | 409 Conflict |
| `QrCodeGenerationException` | 500 Internal Server Error |
| Validation errors | 400 Bad Request |
| Unhandled exceptions | 500 Internal Server Error |

---

## Project Structure

```
src/main/java/com/pd/ticketapp/
├── config/          # Security, JPA auditing, JWT converter
├── controller/      # REST controllers + GlobalExceptionHandler
├── domain/
│   ├── dto/         # Request/response records (internal + API layer)
│   ├── entity/      # JPA entities
│   └── enums/       # EventStatus, TicketStatus, QrCodeStatus, etc.
├── exception/       # Custom exception hierarchy
├── filter/          # UserProvisioningFilter
├── mapper/          # MapStruct interfaces
├── repository/      # Spring Data JPA repositories
└── service/         # Service interfaces + impl/
```
