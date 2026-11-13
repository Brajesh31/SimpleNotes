# Testing Strategy

## Automated validation

The project uses JUnit 4 for local JVM tests and AndroidX test libraries for instrumentation tests.

Run all local JVM tests:

```bash
./gradlew testDebugUnitTest
```

Build the debug APK:

```bash
./gradlew assembleDebug
```

Run connected instrumentation tests when an emulator or physical Android device is available:

```bash
./gradlew connectedDebugAndroidTest
```

The GitHub Actions Android workflow runs JVM unit tests and assembles the debug APK. It does not currently provision an emulator, so instrumentation tests are not part of the hosted CI job.

## NoteFileStore unit coverage

The local JVM tests verify:
1. UTF-8 content with international characters can be written and read.
2. Existing note content can be overwritten.
3. Temporary write files are not left behind after successful writes.
4. A missing file returns `null`.
5. Delete removes content and repeated deletion remains successful.
6. Unsafe, malformed and path-traversal filenames are rejected.
7. The storage directory is created when necessary.

These tests cover the file helper, not Room/fragment integration.

## Instrumentation coverage

The repository's instrumented tests run in an Android process. They verify access to the target application context and exercise NoteFileStore against an Android app cache directory. They require a device/emulator.

## Manual feature test matrix

| Scenario | Expected result |
|---|---|
| First launch with no records | Empty state is visible |
| Create note with title and body | Note appears in list |
| Create with whitespace-only title | User receives validation message |
| Create note with empty body | Empty body is currently allowed |
| Open note | Correct body appears |
| Edit and save | Updated body remains after reopening |
| Create duplicate visible title | Both entries remain distinct by filename |
| Rename note | Title changes; body remains attached to same filename |
| Delete note | List row and body file are removed |
| Share from list | Chooser receives the stored title and body |
| Share from editor | Chooser receives current editor text |
| Missing body file | Editor displays a load error |
| App process restarts | Room records and body files remain |
| Light/dark system mode | Matching theme resources are selected |

## Database test gaps

The current automated JVM tests do not exercise Room. Add instrumentation coverage for:
- insert and list;
- lookup by filename;
- rename while preserving filename;
- deletion;
- ordering by UID;
- schema migration from any previous released schema.

## Storage recovery tests to add

A stronger persistence suite should simulate metadata without a body file, body file without metadata, failed file replacement, low storage, and interrupted create/delete flows. The app currently has no startup reconciliation mechanism.

## What a green CI result means

A passing CI run means the JVM tests and debug APK assembly succeeded in the configured environment. It does not prove all Android devices, accessibility scenarios, backup flows, database migrations, or instrumentation interactions work.
