# Contributing

## Development setup
Open the project in Android Studio with JDK 17 and Android SDK Platform 34 installed. Allow Gradle sync to complete before editing.

## Guidelines
- Follow the existing Kotlin package structure.
- Keep database access in the Room data layer and UI state in ViewModels.
- Use lifecycle-aware observation and avoid retaining Activity or Fragment references.
- Keep user-facing text in Android string resources.
- Do not commit local signing keys, keystores, passwords, private notes, or generated APKs.
- Preserve note data when changing the schema. Add an explicit Room migration when the database version changes.

## Validation
```bash
./gradlew test
./gradlew assembleDebug
```
Run instrumentation tests on an emulator/device when relevant.

## Pull requests
Explain the user-facing behavior, database changes, migration plan, tests, and screenshots. Include Android API levels and device details for platform-specific issues.
