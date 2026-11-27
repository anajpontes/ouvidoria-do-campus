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
- Front-end: HTML and CSS.
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

```powershell
cd backend
$env:SUPABASE_DB_URL = "jdbc:postgresql://<host>:<port>/<database>?sslmode=require"
$env:SUPABASE_DB_USERNAME = "<username>"
$env:SUPABASE_DB_PASSWORD = "<database-password>"
.\mvnw.cmd spring-boot:run
```

Use the host, port, database, and username shown under **Connect** in Supabase. Choose the connection option appropriate for your network; do not commit or share the database password.

### Front-end

```bash
# commands to install dependencies and start the front-end
```

## CI/CD

(describe the GitHub Actions pipeline: what runs on each PR and on each push to `main` — at a minimum, build, lint and tests)

## Deploy

(where the application is published, if applicable; or note that automatic deploy has not been set up yet)

## Links

- **Repository:**
- **Deploy (if any):**
- **Board / GitHub project (if any):**
