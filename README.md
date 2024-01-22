

# Chat API

A simple back-end REST API application designed for a chat system powered by Spring Boot.

## Requirements
 ### Create database
- PostgreSQL 15.5
- [database creation queries](src/main/resources/create_db.sql)

### Auto-generate Jooq code
```bash
  gradlew generateFirstJooq
```
## Features
- Endpoints for authorized and unauthorized users
- Simple chatting endpoints for posting and parsing all messages
- OpenAPI documentation
- Jooq repositories
- Test suites
## REST API

### Auth

`POST /api/auth/login` 

### Messages (User role only)

`GET /api/messages` 

`POST /api/messages`

### Users (Admin role only)
`GET /api/users/{username}/statistics`

`POST /api/users`

`DELETE /api/users/{username}`
