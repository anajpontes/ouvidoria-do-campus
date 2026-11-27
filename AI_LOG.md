# AI usage log (AI_LOG)

**English** · [Português (Brasil)](AI_LOG.pt-BR.md)

Record how the team used artificial intelligence in the project. The use of AI is allowed and encouraged, as long as it is documented: this file is a required deliverable and a script for the “AI in practice” segment of the presentation.

Project documentation must be written in **English** (this file is the main version), with a **Portuguese version** alongside (`AI_LOG.pt-BR.md`). Keep both versions in sync.

Keep this file at the **root** of the repository. Include the tools used and, for every relevant use, the main prompt, what the AI generated, what the team kept and what was corrected or rejected — and why.

Never commit API keys, passwords or other secrets. Those values go in a `.env` file (outside the repository) and in GitHub Secrets.

Copy the template below whenever you need a new entry.

## Tools

- (e.g. ChatGPT, GitHub Copilot, Cursor, other)

## Entry template

```markdown
### [date] — [task or section]
- **Main prompt:**
- **What the AI generated:**
- **What the team kept:**
- **What the team corrected or rejected, and why:**
```

## Log

### 2026-11-26 — Senac visual identity and screen polish
- **Main prompt:** "Improve the screen designs and add the Senac logo."
- **What the AI generated:** a refreshed blue brand palette, responsive navigation and card styling across the front-end screens, using a local Senac logo asset.
- **What the team kept:** the official logo asset was downloaded from the Senac website (`https://www.senac.br/assets/svg/logo.svg`) and bundled locally so page rendering does not depend on a remote image host.
- **What the team corrected or rejected, and why:** kept all existing demo disclosures and interactions; this visual update does not imply affiliation beyond the requested logo or change mock behavior.

### 2026-11-26 — Interactive front-end mock
- **Main prompt:** "Develop the front-end using a mock back-end."
- **What the AI generated:** a local interactive demo for complaint submission, protocol/code tracking, and the staff queue with filters and review actions, plus a mock staff login.
- **What the team kept:** the demo is clearly labeled, uses fictional seeded records, stores state only in the browser session, hides claimant email from the queue view, and does not claim to send emails, upload photos, provide real authentication or run AI.
- **What the team corrected or rejected, and why:** no real API endpoints, authentication rules, code lifecycle, photo storage or AI classification logic were added; those remain back-end/product decisions.

### 2026-11-26 — Require email on new complaints
- **Main prompt:** "The new complaint screen is wrong; email is required."
- **What the AI generated:** updated the complaint form to require an email and aligned the continuity guide and bilingual decision log.
- **What the team kept:** the required email supports sending a protocol and later verification; the email remains hidden from campus staff.
- **What the team corrected or rejected, and why:** kept the photo optional and did not add API behavior; submission remains disabled until the real back-end integration is ready.

### 2026-11-26 — Tracking flow correction
- **Main prompt:** "Correct the tracking screen so it requests only the protocol and lets the back-end send the code."
- **What the AI generated:** replaced the email and code fields with a protocol field while keeping the screen visually consistent.
- **What the team kept:** the screen now represents correctly that the protocol identifies the claimant and that the back-end sends the code.
- **What the team corrected or rejected, and why:** the button remains disabled and no simulated rules or data were created, because the flow still depends on the team's API.

### 2026-11-26 — Complaint tracking screen
- **Main prompt:** "Continue the project's front-end using the continuity guide and implement the complaint lookup/tracking screen without assuming pending code or authentication decisions."
- **What the AI generated:** navigation between the new complaint and tracking views, plus a visual structure for entering the email and confirmation code.
- **What the team kept:** the new screen follows the existing visual pattern, explains email-based tracking and clearly marks states that still require integration.
- **What the team corrected or rejected, and why:** no endpoints, code format, expiration, resend flow or simulated data were created because the guide requires an explicit team decision before those rules.

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
