# Known Limitations

## Product scope

SimpleNotes is a small local Android notes application, not a synchronized or encrypted notes platform.

## Persistence
- Room stores note metadata only.
- Note bodies are separate UTF-8 files.
- Room and filesystem operations cannot share one atomic transaction.
- There is no startup reconciliation for orphan files or missing body files.
- There is no export/import or user-controlled backup workflow.
- No schema migration is defined because the current schema is version 1.

## Security and privacy
- Note bodies are not encrypted by application code.
- Android app-private storage is not end-to-end encryption.
- The manifest has `allowBackup=true`, and custom backup/data extraction rules are not defined.
- Shared note text is passed to another application chosen by the user.
- The app does not implement secure deletion, biometric gating, or privacy mode.

## Note model
- Titles are not unique.
- There are no tags, folders, reminders, pin/archive states, attachments, formatting model, or note search.
- There is no last-modified timestamp or sort-by-modified-time feature.
- There is no note body size limit or storage quota UI.
- Filename generation relies on a high-resolution time value hashed by SHA-256; the hash is not encryption.

## User experience
- Delete currently has no confirmation dialog.
- There is no undo/recycle bin.
- There is no save-progress indicator.
- Error states for filesystem/database partial failures are basic.
- No tablet-specific two-pane layout is defined.
- Instrumentation tests are not run by hosted CI because no emulator is provisioned.

## Operations and release
- The repository does not configure release signing.
- No Play Store publishing workflow exists.
- No analytics, crash reporting, remote backup service, or support backend is integrated.
- Android target SDK and dependencies should be reviewed before a public release to ensure they meet current distribution requirements.
