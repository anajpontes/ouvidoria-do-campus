# [Ouvidoria do Campus]

**English** · [Português (Brasil)](README.pt-BR.md)

Fill in this README and keep it up to date. It is part of the delivery and must remain at the **root** of the repository, together with `DECISIONS.md` and `AI_LOG.md` (and their Portuguese versions: `README.pt-BR.md`, `DECISIONS.pt-BR.md` and `AI_LOG.pt-BR.md`).

Project documentation must be written in **English** (this file is the main version), with a **Portuguese version** alongside. Keep both versions in sync.

Create, inside the repository, the **folders for each part of the project** you will develop (for example, `/backend`, `/frontend`, `/docs`). How you organize them is the team's choice — describe it in [Folder structure](#folder-structure).

On the first day, add the instructor as a repository collaborator, using the email **adrianoanschau@gmail.com**.

## Team

- **Class:** TDS — mixed groups 6570, 6756, and 6863
- **Tech Lead:**
- **Presentation lead:**
- **Members:**
  - Ana Julia — GitHub
  - Vinicius Esteves — GitHub
  - Thomas — GitHub

## Theme / area

Campus infrastructure desk (Ouvidoria do campus) — campus life and community

## Problem

(what problem you are solving, and for whom)

## Solution

(what the application does and what the MVP covers)

## Stack

- Back-end: Java 17 and Spring Boot.
- Front-end: HTML, CSS, and vanilla JavaScript.
- Database: PostgreSQL hosted on Supabase.

## Folder structure

Describe how the repository is organized. Adjust the example below to the team's choice.

```text
/
├── README.md
├── README.pt-BR.md
├── DECISIONS.md
├── DECISIONS.pt-BR.md
├── AI_LOG.md
├── AI_LOG.pt-BR.md
├── backend/
├── frontend/
└── docs/
```

## How to run

### Prerequisites

- Java 17.
- A Supabase project with PostgreSQL access.
- Database connection details from the Supabase dashboard. Keep credentials out of the repository.

### Back-end

Copy `backend/.env.example` to `backend/.env` and set both `SUPABASE_DB_PASSWORD` and
`FOLLOW_UP_EMAIL_ENCRYPTION_KEY` in the local `.env` file. The encryption key must be
Base64-encoded and decode to 32 bytes. Keep the same key for existing encrypted email data.
The `.env` file is ignored by Git; do not commit or share it.

```powershell
cd backend
.\mvnw.cmd spring-boot:run
```

Spring Boot loads the local `.env` file when starting from the `backend` directory. The Supabase **Session Pooler** URL and username have defaults in `application.properties`; override them with environment variables only if your connection details differ.
The API allows requests from `http://localhost:4173` by default. Set `FRONTEND_ORIGIN` if the front-end is served from a different origin.

Before starting the backend for the first time, check the Table Editor to see whether the tables already exist. If they are absent, run the approved migration in [docs/sql/001_create_ouvidoria_tables.sql](docs/sql/001_create_ouvidoria_tables.sql) once from the Supabase SQL Editor. Hibernate is set to `validate` and will not create or alter tables.

### Front-end

From the repository root, run:

```powershell
python -m http.server 4173 --directory frontend
```

Open <http://localhost:4173>. The API base URL is set in the `api-base-url` meta tag on the front-end pages. Reports are submitted to the real backend, and claimants can query status and staff response with the protocol and the shared demo code `xy1234XY`. This shared code is for the school demo only and must not protect sensitive or production data. Email delivery and staff tools remain unavailable.

## CI/CD

(describe the GitHub Actions pipeline: what runs on each PR and on each push to `main` — at a minimum, build, lint and tests)

## Deploy

(where the application is published, if applicable; or note that automatic deploy has not been set up yet)

## Links

- **Repository:**
- **Deploy (if any):**
- **Board / GitHub project (if any):**
