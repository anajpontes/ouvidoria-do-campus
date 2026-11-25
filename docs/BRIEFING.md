# Theme briefing: Campus infrastructure desk

**English** · [Português (Brasil)](BRIEFING.pt-BR.md)

**Hack with AI: Coding with Intelligence**  
Senac na Prática · Technical Program in Systems Development  
Area: campus life and community  
Immersion from 23 to 29 November · presentations on 30 November and 1 December

This briefing is the starting point for **your** theme. Read it carefully, cut an MVP that fits in one week, and record your decisions. You will turn this problem into a real product, with a front end and a back end, an organized repository, and documented use of AI. This is a collaborative immersion: the panel evaluates what the team built, decided, and learned.

## Theme and context

**Campus infrastructure desk** — A channel to report infrastructure problems, with AI classifying and prioritizing tickets.

Air conditioning that does not cool, a projector that fails at the start of class, poor accessibility, a bathroom without maintenance, a lab socket that does not work: the problem appears, someone mentions it in a group chat or at the front desk, and then there is no follow-up. Without a clear channel, the report is lost, the same defect is reported several times, and the person who raised it does not know whether the request was received, prioritized, or fixed.

You will build a straightforward channel to report campus infrastructure problems, in which AI helps classify and prioritize, and the status comes back to the person who opened the ticket.

## Audience and personas

- **A student or staff member who spots a problem:** wants to report it quickly, with a location and, if possible, a photo, and to follow the progress.
- **Someone who receives and organizes tickets (a campus-team role in the MVP):** needs a classified queue, with priority and possible duplicates.
- **The campus community:** benefits when recurring problems stop being invisible.

## Goal

Ship an MVP of an infrastructure ticketing channel in which a person opens a report, AI classifies it and suggests a priority, the team works the queue, and the author sees the status move through to closure.

## Minimum requirements (MVP)

The scope below is realistic for one week. Lock it on the first day and record in `DECISIONS.md` what is in, what is out, and why. A complete, demonstrable flow is better than a long list left half-done.

1. Open a ticket with text, a campus location, and, if possible, a photo.
2. AI classifies the type (for example, electrical, cleaning, accessibility, classroom equipment) and suggests a priority, for human review.
3. A queue view for the campus-team role, with filters by status, type, and priority.
4. Update the status (received, in progress, resolved) and record a short reply.
5. The person who opened the ticket can follow their own ticket.
6. Offer the option of an identified report or one that shows fewer personal details, making clear what that means for follow-up.

## Where AI comes in

The use of AI is allowed and encouraged — in the product and in the process — and it must appear in `AI_LOG.md`. Some concrete starting points for this theme:

- Classify the ticket and extract the location from the text when the person has not picked a point from a list.
- Suggest a priority with a simple, explicit criterion (for example, a safety risk versus occasional discomfort).
- Detect similar tickets that are still open, to reduce duplicates.
- Help rewrite the report when the description is too vague to act on.

Do not publish, without your review, an AI-generated text or automatic decision as if it were already the final version.

## Optional extras

Only after the MVP is standing. These are ideas, not obligations:

- A simple map of the most reported spots.
- Weekly counts by category.
- A short satisfaction question after the ticket is closed.
- A reference time frame (“by when we will look at this”) set by the team, not by AI alone.

## Out of scope

To protect the deadline and the focus of the product:

- A general desk for academic, human-resources, or conduct issues — the MVP is campus infrastructure.
- Legal processes, formal reports of another kind, and replacing official Senac channels for those topics.
- Automatically closing a ticket with only the AI classification, without someone from the team.
- A required integration with an internal system the institution already uses.

## Questions to guide the team

Use these questions on the first day, when you define the MVP, and again before the demo:

- In the MVP, who represents the “campus team” — is a user role enough for the demo?
- How do you handle anonymous or low-data reports, and what can the person still follow?
- What keeps a wave of false or repeated reports from cluttering the queue?
- How is the AI-suggested priority presented, so it does not look like a decision already made?
- What makes a ticket closed, and who can reopen it?

## Deliverables and how the panel evaluates

This is a one-week **collaborative immersion**, with teams of 3 or 4 TDS students. The panel evaluates **your** work, on your own project — formative feedback, not a comparison with other teams.

**What must be in the repository** (at the root), in **English** (the main version) and in **Portuguese** (`.pt-BR`), with a language link at the top of each file:

- `README.md` and `README.pt-BR.md`
- `DECISIONS.md` and `DECISIONS.pt-BR.md`
- `AI_LOG.md` and `AI_LOG.pt-BR.md`

The application must have a **front end and a back end**. The back end may be **Java**, **Python**, or **Node.js** (alone or combined, as long as they are integrated). A front end is required (web, desktop, or mobile). Justify the stack in `DECISIONS.md`. Also required: a Conventional Commits history, work through reviewed Pull Requests, a protected `main` branch, and a green **GitHub Actions** pipeline (build, lint, and tests) on PRs and on `main`. Do not commit secrets. On the first day, add the instructor as a collaborator: **adrianoanschau@gmail.com**.

**Code freeze** is on Sunday, **29 November, at 23:59**. Presentations are **15 minutes + 5 minutes of questions**, on 30 November and 1 December, at Senac Canoas, Room 103. Rehearse the demo and the required outline (problem and solution, live demo, workflow, decisions, AI in practice, and CI/CD).

Evaluation is split equally between **two panels**:

- **Technical panel (50%):** code quality and architecture; Git, Conventional Commits, and PRs; CI/CD; command of the code in the Q&A.
- **Innovation and Impact panel (50%):** originality; impact (the real problem and for whom); user experience; clarity of the pitch; creative use of AI.

Anyone on the team may be asked about any part of the project. Use AI thoughtfully, record what it generated and what you corrected, and own the code you present.

Good work — and may the week be intense, collaborative, and well documented.

