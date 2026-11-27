# Backend and Supabase contract — draft

> **Status: proposal for review only.** This contract is not approved by the team and must not be treated as an implemented API or final database schema.

## Context

The backend is Spring Boot with Spring Data JPA and is currently configured for SQLite. The team has chosen Supabase for the database, and a teammate is designing it. No API endpoints exist yet. This draft is meant to align the backend and database work before implementation.

## Requirements already agreed

- A report has a title, description, and campus location; a photo is optional, but real upload is deferred until storage is ready.
- A complainant must provide an email for verification-code delivery and follow-up.
- The email must not be visible or accessible to the secretary.
- Viewing a report through the public lookup requires an emailed verification code.
- The secretary must sign in to access the queue.
- Staff can filter by status, type, and priority, update a report, and add a short response.
- Status values are `RECEBIDO`, `EM_ANDAMENTO`, and `RESOLVIDO`.
- AI classification and priority are suggestions and require human review.

## Agreed implementation boundaries

- Continue local development with SQLite/JPA while the database owner finalizes the Supabase schema. The planned integration is Supabase PostgreSQL through JPA after that schema is confirmed.
- Store the required email separately from the staff-facing `chamados` data and persist both records atomically. This persistence-only stage does not implement report submission, email-code delivery, public tracking, or staff queue endpoints.
- Defer photo upload until Supabase Storage and its access policy are agreed. Do not save uploads to local disk as a temporary substitute.
- Defer secretary endpoints until Supabase Auth is integrated. Keep the existing suggestion/review fields, but defer actual AI classification until a provider is selected.

## Proposed integration

The agreed direction is to connect Spring Boot to Supabase PostgreSQL with JDBC/JPA, reusing the existing Spring Data JPA dependency, after the database owner confirms the schema. SQLite remains the local development database until then.

Credentials must be supplied through environment variables or another approved secret store. Do not commit credentials or paste them into source code or shared chat.

## Proposed data boundaries

These are suggestions, not a final schema:

- `chamados`: report content, location, status, reviewed type/priority, and staff response. Do not include the complainant's email in fields returned to the secretary.
- Private follow-up data: store the required email separately from the staff-facing report data, with access restricted to the backend follow-up flow. The current persistence stage stores no verification code; do not store a future code in plaintext.
- Photo: upload is deferred; do not store files on local disk. When Supabase Storage is ready, keep only the object reference in the report record and confirm bucket access and upload limits with the database owner.
- The exact table names, field names, types, identifiers, constraints, and review-state representation must be agreed with the database owner.

## Proposed API shape

Route names and payloads below are candidates for discussion, not approved endpoints.

### Public report submission

`POST /api/chamados`

Candidate inputs: title, description, campus location, required follow-up email, and optional photo once storage is integrated. The request encoding (JSON plus separate upload, or multipart form data) depends on the approved photo-storage approach. This endpoint is not part of the current persistence-only stage because email-code delivery is not configured.

The response may include a non-secret report protocol. A protocol alone must not grant access to the report; lookup must also validate the emailed code.

### Public report lookup

The frontend flow has separate steps to request a code and validate it. Candidate routes are:

- `POST /api/acompanhamento/codigo` with the report protocol to send a code to the stored email.
- `POST /api/acompanhamento/consultar` with the report protocol and verification code.

Return only the report details needed by its author; never return the private email. Route names, code expiry, retries, resend behavior, and email provider still need confirmation. These routes are deferred.

### Secretary queue and updates

- `GET /api/secretaria/chamados` with optional status, type, and priority filters.
- `PATCH /api/secretaria/chamados/{id}` to update the reviewed type/priority, status, and short response.

Both routes must require secretary authentication. The authentication method and user/role provisioning are still undecided.

## Decisions needed before implementation

1. Database table and column contract, including identifiers and status/type/priority values.
2. Photo storage: Supabase Storage bucket policy and upload limits.
3. Email provider and verification-code lifecycle (expiry, retries, resend), plus controls that prevent secretary access to private follow-up data.
4. Secretary authentication provisioning and access rules using Supabase Auth.
5. How AI-suggested and human-reviewed type/priority values are represented and which AI provider is used.

Once these points are confirmed, update this draft and implement one backend stage at a time. Do not create the proposed routes or schema as if already approved.
