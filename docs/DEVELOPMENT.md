# Development Guide

## Toolchain

The checked-in project uses:
- Android Gradle Plugin 8.2.0;
- Gradle wrapper 8.2;
- Kotlin Gradle plugin 1.9.22;
- KSP 1.9.22-1.0.17;
- JDK 17;
- Android SDK compile platform 34;
- minimum SDK 26;
- target SDK 34.

Use Android Studio with a compatible Android SDK installation. The Gradle wrapper is committed, so the locally installed Gradle version is not required.

## Initial setup

1. Clone the repository.
2. Install/enable Android SDK Platform 34 and required build tools.
3. Configure JDK 17 in Android Studio.
4. Open the project root and allow Gradle sync to finish.
5. Confirm the wrapper can run:

```bash
./gradlew --version
```

On Windows:

```bat
gradlew.bat --version
```

## Common commands

```bash
./gradlew tasks
./gradlew testDebugUnitTest
./gradlew assembleDebug
./gradlew lintDebug
./gradlew connectedDebugAndroidTest
```

Connected tests require an available Android device or emulator. The regular CI workflow does not launch an emulator.

## Source layout

- `MainActivity.kt`: activity and toolbar setup.
- `NotesApp.kt`: process-level Room and file-store initialization.
- `Utils.kt`: view helpers and SHA-256 filename utility.
- `db/`: Room entity, DAO, database declaration and note file storage.
- `fragment/main/`: notes list, ViewModel and share flow.
- `fragment/editor/`: editor fragment and ViewModel.
- `adapter/`: RecyclerView adapter.
- `res/layout/`: activity, screen, dialog and row layouts.
- `res/navigation/`: navigation graph.
- `res/menu/`: toolbar and contextual menus.
- `res/values/ and values-night/`: strings, colours and themes.
- `src/test/`: JVM tests.
- `src/androidTest/`: device/emulator tests.

## Data access rules

- Never execute Room queries from the main thread.
- Keep database and body-file access in ViewModels or a dedicated repository.
- Use `Note.filename` as the persistent identifier, not the editable title.
- Keep the file naming validator and filename generator consistent.
- Keep file paths inside `filesDir`; do not accept arbitrary paths from UI input.
- Use UTF-8 consistently.
- Preserve the database/file cleanup semantics when changing save or delete flows.

## UI rules

- Observe LiveData with `viewLifecycleOwner`.
- Do not keep view references in a ViewModel.
- Keep visible user copy in string resources.
- Give icon-only actions meaningful content descriptions.
- Preserve native keyboard and back-navigation behaviour.
- Validate user-entered titles after trimming whitespace.

## Room schema changes

Increment the Room database version and add an explicit migration for any schema change. Never discard user notes through destructive migration. Test migrations against populated data.

## Dependency management

Room compiler is configured through KSP. Keep the Room runtime and compiler versions aligned. Update dependency declarations and the Gradle lock/wrapper strategy deliberately; do not mix annotation processing and KSP for the same Kotlin Room compiler.

## Local files and secrets

Do not commit `local.properties`, signing keystores, signing passwords, generated APKs, Gradle build output, or service configuration containing credentials.
