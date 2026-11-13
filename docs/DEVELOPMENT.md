# Development

## Toolchain
- Android Studio compatible with Android Gradle Plugin 8.2.0.
- JDK 17.
- Android SDK Platform 34.
- Android Gradle wrapper included in the repository.

## Build commands
```bash
./gradlew tasks
./gradlew test
./gradlew assembleDebug
```

On Windows use `gradlew.bat`.

## Code organization
- Keep screen logic in fragments and ViewModels.
- Keep persistence contracts in the Room entity and DAO.
- Keep list rendering in the RecyclerView adapter.
- Put layouts and navigation definitions under `res/`.
- Use string resources for user-facing copy.

## Database changes
Add and test Room migrations whenever the schema version changes. Test rename and delete operations against both metadata and note-body files.
