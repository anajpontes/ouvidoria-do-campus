# Backend and Supabase contract — draft

> **Status: draft for remaining work.** The backend's Supabase PostgreSQL connection and `POST /api/chamados` are implemented. Code-based tracking, secretary routes, and the operational policies described here are not implemented or finalized.

## Context

The backend uses Spring Boot, Spring Data JPA, and Supabase PostgreSQL. The `POST /api/chamados` route submits a report; tracking and secretary routes are not implemented. This draft records the current state and the remaining API decisions.

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

- Use Supabase PostgreSQL through JDBC/JPA for the application; Hibernate validates the schema with `ddl-auto=validate`. Integration tests use in-memory H2 and do not connect to the shared database.
- `POST /api/chamados` accepts JSON containing the report and required email, creates a random UUID protocol, and returns the protocol, initial status, and creation time. It does not send an email or include the email in its response.
- Store the required email separately from the staff-facing `chamados` data and persist both records atomically. Encrypt the email with AES-GCM using a 32-byte key supplied through `FOLLOW_UP_EMAIL_ENCRYPTION_KEY`; never commit the key. Key rotation still needs a production policy.
- The JPA mapping includes the private table `acompanhamento_privado` (`chamado_id`, `email_cifrado`) and is validated by Hibernate against the configured Supabase database.
- Email-code delivery, public tracking, and staff queue endpoints are not implemented.
- Defer photo upload until Supabase Storage and its access policy are agreed. Do not save uploads to local disk as a temporary substitute.
- Defer secretary endpoints until Supabase Auth is integrated. Keep the existing suggestion/review fields, but defer actual AI classification until a provider is selected.

## Proposed integration

Spring Boot connects to Supabase PostgreSQL with JDBC/JPA. The application loads local settings from `backend/.env`; tests use H2 to avoid changing the shared database.

Credentials must be supplied through environment variables or another approved secret store. Do not commit credentials or paste them into source code or shared chat.

For local execution, set `SUPABASE_DB_PASSWORD` and `FOLLOW_UP_EMAIL_ENCRYPTION_KEY` in `backend/.env`. The encryption key must be Base64 and decode to 32 bytes. Generate a key locally in PowerShell:

```powershell
$bytes = New-Object byte[] 32
$rng = [System.Security.Cryptography.RandomNumberGenerator]::Create()
$rng.GetBytes($bytes)
[Convert]::ToBase64String($bytes)
$rng.Dispose()
```

Copy the locally generated value to `FOLLOW_UP_EMAIL_ENCRYPTION_KEY` in `.env`. Do not share it. Keep the same key to decrypt previously stored emails; do not save it in the repository.

## Proposed data boundaries

The versioned SQL documents the initial schema. The follow-up flows are still incomplete:

- `chamados`: report content, location, status, reviewed type/priority, and staff response. Do not include the complainant's email in fields returned to the secretary.
- `acompanhamento_privado`: store application-encrypted email separately from staff-facing report data. The current API does not expose this record. Future codes must be stored as a secure hash, never in plaintext.
- Photo: upload is deferred; do not store files on local disk. When Supabase Storage is ready, keep only the object reference in the report record and confirm bucket access and upload limits with the database owner.
- Future changes to tables, fields, and constraints must be agreed with the database owner and delivered as reviewed migrations.

## API shape

The implemented local route is still subject to frontend and schema review. Other routes below are candidates, not approved or implemented endpoints.

### Public report submission

`POST /api/chamados`

Implemented JSON inputs: `title`, `description`, `location`, and required `email`. Photo upload is not supported. The `201 Created` response contains `protocol`, `status`, and `createdAt`; it never includes the email. The protocol is a random UUID string and is not sufficient to access a report.

This route does not send a code. The later lookup flow must require a code sent to the stored email before returning report details.

### Public report lookup

The frontend flow has separate steps to request a code and validate it. Candidate routes are:

- `POST /api/acompanhamento/codigo` with the report protocol to send a code to the stored email.
- `POST /api/acompanhamento/consultar` with the report protocol and verification code.

Return only the report details needed by its author; never return the private email. Route names, code expiry, retries, resend behavior, and email provider still need confirmation. These routes are deferred.

### Secretary queue and updates

- `GET /api/secretaria/chamados` with optional status, type, and priority filters.
- `PATCH /api/secretaria/chamados/{id}` to update the reviewed type/priority, status, and short response.

Both routes must require secretary authentication. The authentication method and user/role provisioning are still undecided.

## Decisions needed for the remaining implementation

1. Database table and column contract, including identifiers and status/type/priority values.
2. Photo storage: Supabase Storage bucket policy and upload limits.
3. Email provider and verification-code lifecycle (expiry, retries, resend), plus controls that prevent secretary access to private follow-up data.
4. Encryption-key storage, rotation, and recovery policy for existing encrypted email data.
5. Secretary authentication provisioning and access rules using Supabase Auth.
6. How AI-suggested and human-reviewed type/priority values are represented and which AI provider is used.

Once these points are confirmed, update this draft and implement one backend stage at a time. Do not create the proposed routes or schema as if already approved.
