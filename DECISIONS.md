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

- **Decision:** use Node.js with Express on the back-end.
- **Alternatives considered:** Java with Spring Boot; Python with FastAPI.
- **Rationale:** the team is already more familiar with JavaScript, and a one-week deadline favors a stack everyone can contribute to from day one.

## [date] — [decision title]

- **Decision:**
- **Alternatives considered:**
- **Rationale:**

## 2026-11-26 — Require an email for complaint tracking

- **Decision:** require an email address when submitting a complaint so the claimant can receive its protocol and follow updates.
- **Alternatives considered:** keep the email optional, which would prevent complaints submitted without one from being tracked through the protocol and verification flow.
- **Rationale:** the tracking flow relies on sending a verification code to the email associated with the complaint. The email remains restricted to claimant tracking and must not be visible to the campus staff.
