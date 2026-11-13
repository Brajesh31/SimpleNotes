# Changelog

This changelog records source and repository changes. It does not imply the app provides encryption, cloud synchronization or a guaranteed backup system.

## Unreleased

- Added NoteFileStore to own UTF-8 body-file reads, writes and deletes.
- Added strict 64-character lowercase hexadecimal filename validation.
- Added temporary-file replacement writes.
- Switched list/editor lookups and navigation to the stable filename instead of the editable title.
- Updated Room list query to return complete note records in descending UID order.
- Fixed RecyclerView list replacement notifications.
- Moved editor and list share body-file access onto IO dispatchers.
- Added cleanup of note body files during delete and best-effort cleanup on failed create.
- Added visible errors for note load, save and share failures.
- Added an accessible content description to the new-note FAB.
- Removed duplicate Room compiler annotation processor configuration in favour of KSP.
- Replaced malformed root .gitignore conflict markers.
- Added JVM tests for note file storage and filename validation.
- Expanded Android CI to run JVM tests and build/upload a debug APK.
- Added detailed architecture, storage, database, file, privacy, security, testing, accessibility, troubleshooting and release documentation.
