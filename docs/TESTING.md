# Testing

## Automated checks
Run:
```bash
./gradlew test
./gradlew assembleDebug
```

Instrumentation tests require a running emulator or connected Android device:
```bash
./gradlew connectedAndroidTest
```

## Manual test cases
- Create a note, close and reopen it, and confirm its content remains.
- Edit a note and confirm the updated content is persisted.
- Rename a note and confirm it still opens.
- Delete a note and confirm it disappears from the list.
- Share a note and verify the chooser receives the intended text.
- Test empty title/content behavior.
- Test process recreation and app restart.

## Data consistency
Verify that Room metadata and the corresponding internal note file are both created, renamed, and deleted as intended.
