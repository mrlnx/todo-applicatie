# TODO Applicatie

Een simpele full-stack applicatie bestaande uit een **frontend** (Angular) en een **backend** (Spring Boot + PostgreSQL).

---
## Requirements
- Docker
- Docker Compose

## Quick Start

### 1. Frontend testen
Build en run de frontend standalone met Nginx:

```bash
cd ./frontend
docker build -t todo-frontend .
docker run -p 8081:80 todo-frontend
```

Frontend is daarna bereikbaar op: http://localhost:8081

### 2. Backend testen
Build en run de backend standalone:

```bash
docker run -d -p 5434:5432 --name postgres -e POSTGRES_PASSWORD=admin123 postgres
```

Klik [hier](#configuratie) om de .env de configureren.

```bash
cd ./backend
docker build -t todo-backend .
docker run -p 8080:8080 todo-backend
```
Backend API is daarna bereikbaar op: http://localhost:8080

### 3. Volledige applicatie starten (met database)

Gebruik Docker Compose om frontend, backend en Postgres database samen te starten:

```bash
docker compose -f docker-compose.yml --env-file .env up --build
```

- Frontend → http://localhost:8081
- Backend → http://localhost:8080
- Database → postgres://localhost:5432

### Configuratie
Maak een .env bestand in de root van het project met de database-gegevens:

```env
DB_NAME=postgres
DB_USERNAME=postgres
DB_PASSWORD=admin123
```
