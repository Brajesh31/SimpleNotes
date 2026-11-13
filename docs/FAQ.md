# Frequently Asked Questions

## Does SimpleNotes require an internet connection?

The current app's note creation, editing, list, rename, delete and local sharing flows do not call a project backend. No internet permission or remote API is declared in the current manifest/source.

## Where are note bodies stored?

As UTF-8 text files in the app's private internal files directory. Room stores each note's title and the corresponding generated filename.

## Is the Room database storing the full note text?

No. Room stores note metadata. The body text is stored in an individual file.

## Why does the app use a filename instead of the note title?

A title can be edited and duplicate titles are allowed. The filename remains stable across rename operations and identifies the correct record.

## Are notes encrypted?

No application-level encryption is implemented. The files are plain UTF-8 text inside Android app-private storage. App sandboxing is not end-to-end encryption.

## Are notes backed up?

The manifest enables Android backup, but the project does not define custom backup/data-extraction rules. Actual behaviour depends on Android version, device settings and platform backup policy. The app does not provide its own guaranteed export/restore feature.

## What happens when I rename a note?

Only the Room title changes. The body filename and file contents remain unchanged.

## What happens when I delete a note?

The Room row is deleted, then the ViewModel attempts to delete its body file. The database and filesystem are separate stores, so the operation is not a single cross-resource transaction.

## Can two notes have the same title?

Yes. The stable filename is used for lookup, navigation, rename, share and delete.

## Why are files written through a temporary file?

It reduces the chance of leaving a partially overwritten body if a write fails. The temporary file is moved over the destination where supported. It does not make a Room database operation atomic with the file operation.

## Does the project have cloud sync?

No. There are no accounts, remote storage or synchronization logic.

## Which Java version is needed?

The configured Android Gradle Plugin 8.2.0 build uses JDK 17. The GitHub Actions workflow runs JDK 17.

## How do I run the tests?

Use `./gradlew testDebugUnitTest`. Instrumentation tests need a running emulator/device and use `./gradlew connectedDebugAndroidTest`.
