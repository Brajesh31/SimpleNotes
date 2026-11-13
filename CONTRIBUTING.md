# Contributing

Thank you for considering a contribution to SimpleNotes. Keep changes compatible with the current native Android/Kotlin architecture unless the pull request explicitly proposes a larger redesign.

## Development environment

- Android Studio with Android SDK Platform 34.
- JDK 17.
- The committed Gradle wrapper.
- An Android API 26+ emulator/device for manual and instrumentation testing.

Start by checking the toolchain:

```bash
./gradlew --version
```

## Build and test

```bash
./gradlew testDebugUnitTest
./gradlew assembleDebug
./gradlew lintDebug
```

For device tests:

```bash
./gradlew connectedDebugAndroidTest
```

## Architecture guidelines

- Keep UI rendering and navigation in Fragments/Activity.
- Keep asynchronous persistence work in ViewModels or a dedicated repository.
- Use `viewLifecycleOwner` for fragment view observers.
- Run database and filesystem operations on IO dispatchers.
- Use `Note.filename` as the stable note identity.
- Keep user-visible text in string resources.
- Keep storage path validation inside NoteFileStore.
- Do not move note-file access into RecyclerView binding or view rendering.
- Do not use editable title text as a database lookup key.

## Persistence and migrations

Room metadata and note bodies are stored separately. Changes to either side must preserve their relationship. Any Room schema change must increment the database version and include a migration that preserves existing user notes. Do not add destructive migration fallback.

When changing file layout or filename format, provide a migration/compatibility strategy for existing files. Do not silently invalidate old records.

## Kotlin and Android conventions

- Follow Kotlin official style.
- Prefer immutable values and narrow responsibilities.
- Use suspend DAO functions.
- Keep coroutine scopes lifecycle-aware.
- Use stable IDs for adapters and navigation.
- Add meaningful error handling for file/database operations.
- Keep accessibility labels and touch targets in the UI.
- Do not log note bodies.

## Privacy and security

- Never include real note contents in tests, screenshots, issue templates, sample data or logs.
- Do not add permissions without documenting why they are needed.
- Do not claim encryption unless it is implemented and tested.
- Keep signing keys and passwords out of Git.
- Review backup/data-extraction behaviour before adding a sensitive-data feature.
- Treat Android share targets as external recipients of note contents.

## Pull request checklist

- [ ] Explain the user-visible problem and expected behaviour.
- [ ] Describe data model and storage changes.
- [ ] Add or update unit/instrumentation tests.
- [ ] Run JVM tests and assemble debug APK.
- [ ] Review Android lint output.
- [ ] Verify create, open, edit, rename, delete and share flows when relevant.
- [ ] Test light and dark theme.
- [ ] Confirm no note contents, credentials or signing material are committed.
- [ ] Update docs, roadmap and changelog where relevant.
- [ ] Include screenshots for UI changes, using fictional note content only.

## Scope discipline

Do not turn a small UI pull request into an unreviewed database, encryption, backup, or synchronization redesign. Those features require explicit data migration and threat-model work.
