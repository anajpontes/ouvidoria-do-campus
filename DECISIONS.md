# Project decisions

**English** · [Português (Brasil)](DECISIONS.pt-BR.md)

Record the team's important decisions here. Each entry should be short and state what was decided, the alternatives considered and the reason for the choice.

Project documentation must be written in **English** (this file is the main version), with a **Portuguese version** alongside (`DECISIONS.pt-BR.md`). Keep both versions in sync.

Keep this file at the **root** of the repository. Add a new entry for every relevant decision (stack, architecture, MVP scope, what was left out, paths you tried and abandoned, etc.). This file is a script for the decisions segment of the presentation.

Copy the template below whenever you need a new entry.

## Template

```markdown
## [date] — [decision title]
- **Decision:**
- **Alternatives considered:**
- **Rationale:**
```

## 23/11/2026 — Back-end stack choice (example)

- **Decision:** use Java with Spring on the back-end.
- **Alternatives considered:** Java with Spring Boot; Python with FastAPI.
- **Rationale:** the team is already more familiar with JavaScript, and a one-week deadline favors a stack everyone can contribute to from day one.

## 24/11/26 — MVP scope

- **Decision:** create a system where users can open tickets with text, a photo, and the campus location.
- **Alternatives considered:** disallow audio, files (except PNG and MP4), and links.
- **Rationale:** this scope meets the briefing's minimum requirements and can be developed within the available time. The team chose text, photos, and campus location as the most effective way to report a problem.

## 26/11/2026 — Private email-based tracking

- **Decision:** A complainant may optionally provide an email address to receive a reference code and track the complaint. To view the complaint through the lookup screen, they must confirm a code sent to that address. The email will be used only for tracking and will not be visible or accessible to the campus secretary.
- **Alternatives considered:** Do not offer email-based tracking, or allow lookup without verifying a code sent to the provided address.
- **Rationale:** This allows complainants to track a report without requiring them to identify themselves to the secretary. People who do not provide an email can submit a report without sharing that personal data.

## 26/11/2026 — Initial frontend delivery

- **Decision:** First build the responsive report-submission screen with HTML and CSS. Keep submission disabled until a real API exists; implement the secretary login and code-based lookup/verification in later steps.
- **Alternatives considered:** Build every screen and simulate integration before the back-end endpoints are available.
- **Rationale:** Avoid inventing APIs or presenting a simulated submission as functional, and keep development in small steps.

## 26/11/2026 — Supabase database

- **Decision:** Use Supabase as the project's database instead of the previously configured SQLite. The teammate responsible for the database will define its schema; the Spring Boot integration will follow the agreed schema and connection method.
- **Alternatives considered:** Keep using the existing SQLite configuration.
- **Rationale:** The team chose Supabase and assigned database ownership to a teammate. Agreeing on the schema and connection method first avoids implementing against an assumed contract.

## 26/11/2026 — Initial JPA report model

- **Decision:** Prepare the `Chamado` JPA entity against the proposed report fields, keeping the email out of the staff-facing report model. Keep the existing SQLite configuration for local mapping tests until the Supabase schema and connection method are confirmed.
- **Alternatives considered:** Wait for the database schema before writing any domain model, or switch the connection configuration before the Supabase contract is ready.
- **Rationale:** The core report fields and statuses are already known, so their mapping can be reviewed and tested locally without claiming that the proposed Supabase schema or API is final.

## 27/11/2026 — Initial backend persistence scope

- **Decision:** Use SQLite/JPA for local development until the teammate confirms the Supabase schema, then integrate with Supabase PostgreSQL through JPA. The complainant's email is required and is stored in a separate private follow-up entity, not in `Chamado`. Keep photo upload deferred until Supabase Storage is ready. Defer email-code delivery and tracking endpoints until an email provider and code lifecycle are agreed; defer secretary queue endpoints until Supabase Auth is integrated. Keep AI suggestion/review fields, but do not claim real AI classification until a provider is selected.
- **Alternatives considered:** Wait for the database before all backend work, store email with staff-facing report data, save photos to local disk temporarily, or expose routes before their required email/authentication services exist.
- **Rationale:** This allows safe local backend work without coupling to an unapproved schema or falsely claiming that tracking, photos, AI, or staff login are complete.

## 27/11/2026 — Public report submission and private email encryption

- **Decision:** Implement `POST /api/chamados` locally with JSON fields `title`, `description`, `location`, and required `email`. Return a random UUID protocol, initial status, and creation time, but never the email. Store the email separately using AES-GCM with a 32-byte Base64 key supplied through `FOLLOW_UP_EMAIL_ENCRYPTION_KEY`. Align the current private JPA mapping with the database branch's proposed schema, pending the database owner's approval.
- **Alternatives considered:** Keep only the persistence service without an HTTP route, store the email in plaintext, or expose a staff-facing route before authentication is available.
- **Rationale:** Provide a real, testable report-submission API while protecting the private email and keeping the implementation compatible with the current schema proposal. Code delivery/lookup, staff routes, photos, and real AI remain separate pending work.
