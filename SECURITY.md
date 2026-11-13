# Security Policy

## Scope
SimpleNotes is a local Android notes application. The current implementation stores note content in app-private files and note metadata in Room.

## Data protection notes
- The project does not document application-level encryption for note content.
- Android app-private storage provides sandboxing but is not a substitute for encryption at rest or secure device configuration.
- Review Android backup behavior before handling sensitive notes.
- Do not share real note contents, credentials, or personal data in public bug reports.

## Reporting
Do not disclose exploitable details publicly. Use GitHub private vulnerability reporting when available, or contact the repository owner privately with impact and reproduction steps.

## Release hygiene
Do not commit signing keystores, passwords, API tokens, or release credentials. Keep dependencies updated and review Android security guidance.
