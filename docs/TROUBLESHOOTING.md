# Troubleshooting

## Gradle cannot find Java

The project uses Android Gradle Plugin 8.2.0 and CI uses JDK 17. Configure Android Studio's Gradle JDK to a JDK 17 installation. Check:

```bash
./gradlew --version
```

The output should report JVM 17.

## Android SDK is missing

Install Android SDK Platform 34 and the build tools requested by Android Studio. Set the local SDK path through Android Studio or the ignored `local.properties` file. Never commit local machine SDK paths.

## Gradle sync fails

- Confirm internet access to Google Maven, Maven Central and Gradle Plugin Portal.
- Use the committed Gradle wrapper.
- Check Android Gradle Plugin, Kotlin and KSP versions remain compatible.
- Remove stale Gradle caches only as a diagnostic step, not as a permanent fix.
- Read the first meaningful dependency/configuration error rather than the final generic task failure.

## Unit tests fail

Run:

```bash
./gradlew testDebugUnitTest --stacktrace
```

The NoteFileStore unit tests use a temporary directory on the host JVM. Check file permission, move/rename support, UTF-8 expectations, and filename validation.

## Debug APK does not assemble

Run:

```bash
./gradlew assembleDebug --stacktrace
```

Check:
- JDK 17;
- Android SDK Platform 34;
- Gradle wrapper 8.2;
- Kotlin/KSP plugin resolution;
- Room compiler configured via KSP;
- Android resource XML syntax;
- generated ViewBinding class references.

## A note appears in the list but cannot open

The Room row may refer to a body file that is missing or unreadable. The editor reports a load error rather than crashing. The current app has no automatic repair/export flow. Avoid manually deleting app-private files unless the data is backed up.

## Notes disappear after uninstall or app-data clear

This is expected for app-private Room and file storage. The project does not implement a guaranteed export or cloud restore flow. Android backup may depend on device/version settings; the manifest currently allows backup but there are no custom rules in the repository.

## Rename changes title but not body filename

That is intentional. The filename is the stable note identity and body-file key. Rename updates only the Room title.

## Duplicate note titles

Duplicate display titles are permitted. Each note uses its own filename as identity, so opening, sharing, renaming and deleting a row still address the selected record.

## A deleted note file remains on disk

The database row is deleted and file removal is attempted on IO. If the filesystem rejects deletion, an orphan file can remain. There is currently no startup orphan-file cleanup task.

## Share chooser does not open

Check that the note metadata and body file both exist. List sharing reads content on IO and displays an error if it cannot load. Also verify the Android device has an app capable of receiving text through ACTION_SEND.

## Connected instrumentation test fails

Use a booted emulator or connected physical device with API 26 or newer. Run:

```bash
./gradlew connectedDebugAndroidTest
```

The GitHub Actions build currently runs host unit tests and APK assembly, not emulator-based instrumentation.

## Room schema changed unexpectedly

Increment the Room database version and add an explicit migration. Do not use destructive migration fallback for notes. Test upgrade using a database containing records and matching text files.
