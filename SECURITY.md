# Security Policy

## Current security posture

SimpleNotes stores note metadata in Room and note bodies as plain UTF-8 files in Android app-private internal storage. It does not implement application-level encryption, end-to-end encryption, secure deletion, or cloud security controls.

The Android manifest currently allows backup. The repository does not define custom backup/data-extraction rules. Do not assume the notes are excluded from device backup.

The app uses the Android share chooser to pass note content to a selected application. That destination is outside this repository's control.

## Supported scope

Security reports are accepted for:
- path traversal or unexpected file access through note filenames;
- note data corruption, unintended disclosure, or cross-record access;
- unsafe intent handling or exported component behaviour;
- build/release configuration that exposes signing or service credentials;
- dependency vulnerabilities with a credible impact on this app.

## Reporting

Do not publish note text, personal information, device backup archives, passwords, private keys, or signing credentials in public issues.

Report suspected vulnerabilities privately to the repository owner. Include the affected commit/app version, Android version, device context, impact, preconditions, and safe reproduction steps. Remove all note contents and private identifiers from logs.

## Contributor requirements

- Do not log note body contents.
- Do not accept arbitrary filesystem paths from UI input.
- Keep note file operations inside the app-private directory.
- Validate generated filename identifiers.
- Preserve user data through Room migrations.
- Do not add permissions or external SDKs without a clear purpose.
- Keep keystores and signing passwords out of source control.
- Document backup, sharing and data retention changes.

## Security claims

Do not describe this app as an encrypted vault, password manager, end-to-end private messenger, or guaranteed-backup solution. Those protections are not implemented in the current repository.
