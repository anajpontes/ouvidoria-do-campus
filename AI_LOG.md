# AI usage log (AI_LOG)

**English** · [Português (Brasil)](AI_LOG.pt-BR.md)

Record how the team used artificial intelligence in the project. The use of AI is allowed and encouraged, as long as it is documented: this file is a required deliverable and a script for the “AI in practice” segment of the presentation.

Project documentation must be written in **English** (this file is the main version), with a **Portuguese version** alongside (`AI_LOG.pt-BR.md`). Keep both versions in sync.

Keep this file at the **root** of the repository. Include the tools used and, for every relevant use, the main prompt, what the AI generated, what the team kept and what was corrected or rejected — and why.

Never commit API keys, passwords or other secrets. Those values go in a `.env` file (outside the repository) and in GitHub Secrets.

Copy the template below whenever you need a new entry.

## Tools

- GitHub Copilot.

## Entry template

```markdown
### [date] — [task or section]
- **Main prompt:**
- **What the AI generated:**
- **What the team kept:**
- **What the team corrected or rejected, and why:**
```

## Log

### 24/11/2026 — JWT authentication (example)

- **Main prompt:** "Generate a JWT authentication middleware in Node.js/Express, with expired-token handling and a sample protected route."
- **What the AI generated:** a middleware and a sample route, with an outdated dependency and no validation of environment variables.
- **What the team kept:** the middleware structure and the token verification flow.
- **What the team corrected or rejected, and why:** we updated the library, moved the secret to an environment variable and added tests. The sample route was rewritten to fit our user model.

### [date] — [task or section]

- **Main prompt:**
- **What the AI generated:**
- **What the team kept:**
- **What the team corrected or rejected, and why:**

### 27/11/2026 — Public report API and private email storage

- **Main prompt:** Continue the backend so it can accept reports locally and later align with the teammate's Supabase database without exposing the complainant's email.
- **What the AI generated:** A JSON submission endpoint, UUID protocol generation, JPA mapping aligned with the current schema proposal, AES-GCM email encryption using an environment key, and tests/documentation.
- **What the team kept:** Local SQLite/JPA development, the required private email, and a response that omits the email.
- **What the team corrected or rejected, and why:** Email-code delivery, staff endpoints, photo upload, and real AI classification remain deferred because their providers/policies or authentication are not yet approved. The proposed SQL was not executed and no Supabase credentials were added.
