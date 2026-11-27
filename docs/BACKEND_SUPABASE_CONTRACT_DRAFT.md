# Backend and Supabase contract — draft

> **Status: proposal for review only.** This contract is not approved by the team and must not be treated as an implemented API or final database schema.

## Context

The backend is Spring Boot with Spring Data JPA and is currently configured for SQLite. The team has chosen Supabase for the database, and a teammate is designing it. No API endpoints exist yet. This draft is meant to align the backend and database work before implementation.

## Requirements already agreed

- A report has a title, description, and campus location; a photo is optional.
- A complainant may optionally provide an email for the protocol and follow-up.
- The email must not be visible or accessible to the secretary.
- Viewing a report through the public lookup requires an emailed verification code.
- The secretary must sign in to access the queue.
- Staff can filter by status, type, and priority, update a report, and add a short response.
- Status values are `RECEBIDO`, `EM_ANDAMENTO`, and `RESOLVIDO`.
- AI classification and priority are suggestions and require human review.

## Proposed integration

**Recommendation for review:** connect Spring Boot to Supabase PostgreSQL with JDBC/JPA, reusing the existing Spring Data JPA dependency. The database owner must confirm whether this is the intended connection method or whether the Supabase REST API will be used instead.

Credentials must be supplied through environment variables or another approved secret store. Do not commit credentials or paste them into source code or shared chat.

## Proposed data boundaries

These are suggestions, not a final schema:

- `chamados`: report content, location, status, reviewed type/priority, and staff response. Do not include the complainant's email in fields returned to the secretary.
- Private follow-up data: store the email and verification state separately from the staff-facing report data, with access restricted to the backend follow-up flow. Do not store a verification code in plaintext.
- Photo: if Supabase Storage is selected, keep only the object reference in the report record; confirm bucket access and upload limits with the database owner.
- The exact table names, field names, types, identifiers, constraints, and review-state representation must be agreed with the database owner.

## Proposed API shape

Route names and payloads below are candidates for discussion, not approved endpoints.

### Public report submission

`POST /api/chamados`

Candidate inputs: title, description, campus location, optional photo, and optional follow-up email. The request encoding (JSON plus separate upload, or multipart form data) depends on the approved photo-storage approach.

The response may include a non-secret report protocol. A protocol alone must not grant access to the report; lookup must also validate the emailed code.

### Public report lookup

`POST /api/acompanhamento/consultar`

Candidate inputs: report protocol and verification code. Return only the report details needed by its author; never return the private email. Code expiry, retries, and resend behavior still need a decision.

### Secretary queue and updates

- `GET /api/secretaria/chamados` with optional status, type, and priority filters.
- `PATCH /api/secretaria/chamados/{id}` to update the reviewed type/priority, status, and short response.

Both routes must require secretary authentication. The authentication method and user/role provisioning are still undecided.

## Decisions needed before implementation

1. Supabase connection method: PostgreSQL JDBC/JPA or Supabase REST API.
2. Database table and column contract, including identifiers and status/type/priority values.
3. Photo storage: Supabase Storage or another approved approach; bucket policy and upload limits.
4. Private email access boundary and verification-code lifecycle (expiry, retries, resend).
5. Secretary authentication method and access rules.
6. How AI-suggested and human-reviewed type/priority values are represented.

Once these points are confirmed, update this draft and implement one backend stage at a time. Do not create the proposed routes or schema as if already approved.
