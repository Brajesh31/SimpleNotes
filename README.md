# SimpleNotes

SimpleNotes is a native Android notes application written in Kotlin. It uses Android Jetpack components, Room for local note metadata, Navigation Component for screen navigation, ViewModel and LiveData for UI state, and RecyclerView for the notes list.

## Features visible in the current code
- Create and edit notes through the editor screen.
- List notes in the main screen.
- Rename and delete notes.
- Share note text through Android's share chooser.
- Store note metadata using Room.
- Store note content as files in the app's internal files directory.

## Technology
- Kotlin
- Android Gradle Plugin 8.2.0
- Kotlin Gradle plugin 1.9.22
- KSP 1.9.22-1.0.17
- AndroidX Room 2.6.1
- Navigation Component 2.7.6
- ViewModel and LiveData
- Material Components
- RecyclerView

## Requirements
- Android Studio with a compatible Android SDK.
- JDK 17 for the Android Gradle Plugin 8.x toolchain.
- Android SDK Platform 34.
- Android device or emulator running API 26 or newer.

## Build
Open the project in Android Studio and allow Gradle sync to complete. From a terminal:

```bash
./gradlew tasks
./gradlew test
./gradlew assembleDebug
```

On Windows, use `gradlew.bat` instead of `./gradlew`.

The debug APK is normally generated under `app/build/outputs/apk/debug/`.

## Project structure
```text
app/src/main/java/com/makuta/simplenotes/
  MainActivity.kt
  NotesApp.kt
  adapter/              RecyclerView adapter
  db/                   Room entity, DAO and database
  fragment/main/        Notes list screen and ViewModel
  fragment/editor/      Note editor screen and ViewModel
app/src/main/res/
  layout/               ViewBinding layouts
  navigation/           Navigation graph
  values/               Strings, colors and themes
```

## Data storage
The Room database stores note metadata. Note text is stored in the application's private internal files directory, and the database record refers to the filename. Uninstalling the app removes app-private data. Back up or export notes before uninstalling if you need to retain them.

## Tests
The repository includes local unit and instrumentation test source sets. Run `./gradlew test` and `./gradlew connectedAndroidTest` when an emulator/device is available.

## Security and privacy
Notes are stored locally in app-private storage, but the repository does not document an encryption-at-rest layer. Android app sandboxing is not equivalent to end-to-end encryption. Review backup settings and device security before storing sensitive information. See [docs/SECURITY.md](docs/SECURITY.md).

## Documentation
- [Architecture](docs/ARCHITECTURE.md)
- [Development](docs/DEVELOPMENT.md)
- [Database and storage](docs/DATA_STORAGE.md)
- [Testing](docs/TESTING.md)
- [Deployment](docs/DEPLOYMENT.md)

## Contributing and license
See [CONTRIBUTING.md](CONTRIBUTING.md). This project is licensed under MIT, see [LICENSE](LICENSE).
