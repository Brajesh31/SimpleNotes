# SimpleNotes

SimpleNotes is a native Android note-taking app written in Kotlin. It provides a local notes list, a multiline editor, rename/delete actions, and Android's standard text-sharing chooser.

The app uses Room for note metadata and separate UTF-8 files in Android's app-private internal storage for note bodies. The list and editor screens use ViewModel and LiveData, navigation is handled by Android Navigation Component, and note titles are rendered with RecyclerView.

> **Privacy and product scope:** The current version is local-only. Note bodies are plain UTF-8 files, not application-encrypted. The manifest allows Android backup, but the repository does not define custom backup/data-extraction rules. There is no account, cloud sync, end-to-end encryption, attachment support, note search, or guaranteed export/restore workflow.

## Features

- Create a note with a title and multiline body.
- View saved notes in a RecyclerView.
- Open and edit an existing note.
- Rename a note without changing its body-file identity.
- Delete a note and attempt to remove its content file.
- Share note text through Android's system chooser.
- Store note metadata in Room.
- Store note bodies as UTF-8 files in app-private internal storage.
- Light and dark Material 3 theme resources.
- Stable filename-based note identity, so duplicate display titles can coexist.

## Technology

| Area | Technology |
|---|---|
| Language | Kotlin |
| UI | Android XML layouts and ViewBinding |
| App framework | AndroidX AppCompat and Fragment |
| UI components | Material Components, ConstraintLayout, RecyclerView |
| Navigation | Android Navigation Component |
| UI state | ViewModel, LiveData and viewModelScope |
| Local database | Room 2.6.1 |
| Room compiler | KSP |
| Build | Gradle 8.2 and Android Gradle Plugin 8.2.0 |
| Tests | JUnit 4 and AndroidX instrumentation tests |

## Requirements

- Android Studio with a compatible Android SDK installation.
- JDK 17.
- Android SDK Platform 34.
- Android device or emulator with API 26 or newer.
- Internet access during initial Gradle dependency resolution.

The repository includes the Gradle wrapper. Use it rather than depending on a system-wide Gradle installation.

## Build and run

Open the repository root in Android Studio and allow Gradle sync to complete.

From a terminal:

```bash
./gradlew testDebugUnitTest
./gradlew assembleDebug
```

On Windows:

```bat
gradlew.bat testDebugUnitTest
gradlew.bat assembleDebug
```

The debug APK is normally generated at:

```text
app/build/outputs/apk/debug/app-debug.apk
```

Install it on a compatible emulator or device to test the UI.

## Common Gradle commands

| Command | Purpose |
|---|---|
| `./gradlew tasks` | List available Gradle tasks |
| `./gradlew testDebugUnitTest` | Run local JVM tests |
| `./gradlew assembleDebug` | Build debug APK |
| `./gradlew lintDebug` | Run Android lint |
| `./gradlew connectedDebugAndroidTest` | Run instrumentation tests on a connected device/emulator |
| `./gradlew clean` | Remove generated build output |

## Project structure

```text
.
├── app/
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── java/com/makuta/simplenotes/
│       │   │   ├── MainActivity.kt
│       │   │   ├── NotesApp.kt
│       │   │   ├── Utils.kt
│       │   │   ├── adapter/
│       │   │   ├── db/
│       │   │   └── fragment/
│       │   │       ├── editor/
│       │   │       └── main/
│       │   └── res/
│       │       ├── drawable/
│       │       ├── layout/
│       │       ├── menu/
│       │       ├── navigation/
│       │       ├── values/
│       │       └── values-night/
│       ├── test/
│       └── androidTest/
├── docs/
├── gradle/
│   └── wrapper/
├── .github/
│   └── workflows/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── gradlew
```

## Storage model

Room stores the note title, database ID and generated body filename. The actual note text is stored separately as UTF-8 in the app's internal files directory.

The filename is the stable identifier. A note can be renamed without moving the body file, and two notes can share the same title without colliding in normal list/editor operations.

New note creation writes the body file and inserts its metadata record. Editing replaces the file contents. Deletion removes the database row and makes a best-effort attempt to delete the body file. Since Room and the filesystem are separate resources, these operations cannot form one atomic transaction.

See [Data Storage](docs/DATA_STORAGE.md), [Database Contract](docs/DATABASE.md), and [File Storage](docs/FILE_STORAGE.md).

## Tests and CI

The repository includes:
- JVM tests for NoteFileStore UTF-8 read/write, overwrite, delete, directory creation, and filename validation.
- Instrumented tests that require an Android device or emulator.
- GitHub Actions validation for host unit tests and debug APK assembly.

Run the local test suite:

```bash
./gradlew testDebugUnitTest
```

Run device tests when an emulator is available:

```bash
./gradlew connectedDebugAndroidTest
```

The hosted CI workflow does not currently provision an emulator, so a passing CI run does not mean instrumentation tests were run.

## Important limitations

- No application-level encryption at rest.
- No end-to-end encryption or cloud synchronization.
- No user accounts or remote backup service.
- No custom Android backup/data-extraction rules.
- No guaranteed export/import or restore feature.
- No full-text search, tags, folders, reminders or attachments.
- No automatic reconciliation for orphan files or missing body files.
- Duplicate note titles are allowed.
- Delete has no confirmation or undo flow.
- Database schema is currently version 1 and no migration is defined yet.

Do not use this version as a secure password vault or claim encrypted/private-vault capabilities.

## Documentation

| Guide | Contents |
|---|---|
| [Architecture](docs/ARCHITECTURE.md) | Startup, screens, components and storage boundaries |
| [Component Reference](docs/COMPONENT-REFERENCE.md) | Kotlin classes and Android resources |
| [User Flows](docs/USER-FLOWS.md) | Create, edit, rename, delete and share behaviour |
| [ViewModel State](docs/VIEWMODEL-STATE.md) | LiveData contracts and lifecycle |
| [Data Model](docs/DATA_MODEL.md) | Note metadata and stable identity |
| [Database](docs/DATABASE.md) | Room entity, DAO and migration policy |
| [Data Storage](docs/DATA_STORAGE.md) | Metadata/body persistence lifecycle |
| [File Storage](docs/FILE_STORAGE.md) | Filename validation, UTF-8 and atomic write strategy |
| [Development](docs/DEVELOPMENT.md) | Toolchain, commands and code conventions |
| [Dependencies](docs/DEPENDENCIES.md) | Build and runtime dependency inventory |
| [Testing](docs/TESTING.md) | Automated and manual validation |
| [Deployment](docs/DEPLOYMENT.md) | APK build and release preparation |
| [Accessibility](docs/ACCESSIBILITY.md) | Screen reader, keyboard and theme checks |
| [Security](docs/SECURITY.md) | Threat boundaries and security limitations |
| [Privacy and Backup](docs/PRIVACY-AND-BACKUP.md) | Local data, Android backup and retention |
| [Operations](docs/OPERATIONS.md) | Support, diagnostics and data integrity |
| [Troubleshooting](docs/TROUBLESHOOTING.md) | Common development and runtime issues |
| [Known Limitations](docs/KNOWN-LIMITATIONS.md) | Missing features and risks |
| [Roadmap](docs/ROADMAP.md) | Proposed improvements |
| [FAQ](docs/FAQ.md) | Common questions |
| [Asset Inventory](docs/ASSET-INVENTORY.md) | Launcher, drawable, layout and screenshot files |

## Contributing and license

Read [CONTRIBUTING.md](CONTRIBUTING.md) before opening a pull request. Do not place real note contents, credentials, signing keys, or personal data in source files, tests, screenshots, or issues.

The existing MIT license is retained in [LICENSE](LICENSE).

Last updated: 2026-11-14 04:55 UTC
