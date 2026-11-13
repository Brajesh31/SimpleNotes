# Release Checklist

## Build and tests

- [ ] Confirm JDK 17 and Android SDK Platform 34.
- [ ] Run `./gradlew testDebugUnitTest`.
- [ ] Run `./gradlew assembleDebug`.
- [ ] Run `./gradlew lintDebug` and review findings.
- [ ] Run connected instrumentation tests on a supported emulator/device.
- [ ] Confirm the debug artifact corresponds to the intended commit.
- [ ] Review dependency and Gradle plugin updates.

## User data and persistence

- [ ] Create, edit, reopen, rename, share and delete a note.
- [ ] Verify duplicate display titles remain independently editable.
- [ ] Verify body filenames remain unchanged after rename.
- [ ] Verify deleting a note removes its body file.
- [ ] Verify missing body files produce a clear error.
- [ ] Test process termination and app restart.
- [ ] Test upgrade from the previous released version with populated notes.
- [ ] Review Room schema version and migration coverage.
- [ ] Review create/delete failure recovery and orphan-file handling.

## Privacy and security

- [ ] Review `android:allowBackup` behaviour on target Android versions.
- [ ] Decide whether custom backup/data-extraction rules are required.
- [ ] Keep note contents out of logs, screenshots and issue reports.
- [ ] Review intent sharing behaviour.
- [ ] Confirm no unnecessary permissions or network dependencies were added.
- [ ] Do not claim application-level encryption or secure deletion.
- [ ] Review signing and keystore handling.

## UI and accessibility

- [ ] Test light and dark themes.
- [ ] Test TalkBack labels for FAB, notes and menu actions.
- [ ] Test large font/display scaling.
- [ ] Test portrait, landscape and small screens.
- [ ] Test keyboard and system back navigation.
- [ ] Confirm save/load/share errors are perceivable.

## Release configuration

- [ ] Confirm application ID and version code/name.
- [ ] Prepare protected release signing.
- [ ] Build a signed release APK/AAB.
- [ ] Test fresh install and in-place upgrade.
- [ ] Confirm backup and retention disclosures are accurate.
- [ ] Update release notes and changelog.
- [ ] Preserve the source commit SHA and signed build artifact.

Do not publish a release while data migration, backup behaviour, or a reproducible user-data-loss issue is unresolved.
