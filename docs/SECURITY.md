# Security Policy and Trust Boundaries

## Current security model

SimpleNotes is a local Android application. Its notes are stored in the application's private internal files directory and its Room database. The project does not use a backend, user account, remote synchronization, or third-party note service.

The Android app sandbox provides isolation from ordinary applications. It is not equivalent to encryption at rest or end-to-end encryption.

## Sensitive content warning

Note bodies are stored as plain UTF-8 text files. The project does not encrypt those files with a user-controlled key. The manifest currently enables Android backup with `android:allowBackup="true"`; backup and device-transfer behavior depends on Android version, system settings and configured policies. Do not assume notes are excluded from backup.

Users should avoid storing passwords, authentication codes, financial account details, identity documents, or other high-impact secrets in the current implementation.

## Filename validation

Note body file names must be 64 lowercase hexadecimal characters. This matches the SHA-256 filename generator and prevents a database filename containing path separators or traversal segments from being used as a path. This validation is path-safety hardening, not encryption.

## Filesystem operations

NoteFileStore is restricted to the provided app-private directory. It reads, writes, and deletes only validated names. Writes use a temporary file followed by a replacement move where supported. Errors are returned to the ViewModel and surfaced to the user for load/save operations.

## Sharing

The app launches Android's standard text share chooser. Once content is handed to another application, that recipient app's privacy and retention rules apply. The user should review the selected destination before sharing sensitive note text.

## Database and filesystem consistency

Room metadata and note-body files are separate resources. A crash during a create/delete operation can still leave inconsistent state. The app has no reconciliation screen or automatic backup/restore validator.

## Reporting

Do not publish note contents, personal information, device backups, signing keys, or credentials in public issues. Report security concerns privately to the repository owner with:
- affected app/revision;
- Android version and device context;
- impact and preconditions;
- safe reproduction steps;
- sanitized logs without note contents.

## Production security work still required

Before marketing this as a secure/private vault, design and independently review:
- encryption at rest and key lifecycle;
- backup exclusion or encrypted backup policy;
- biometric/device credential gating if required;
- secure export/import;
- screen capture and recent-app preview policy;
- data deletion and recovery guarantees;
- threat model for rooted/compromised devices;
- retention and incident response.

Do not claim encryption, end-to-end privacy, or secure deletion until those controls are implemented and tested.
