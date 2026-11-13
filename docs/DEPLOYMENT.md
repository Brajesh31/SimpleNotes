# Deployment and Distribution

## Current build product

SimpleNotes is an Android application module. The default build type is debug. The project does not currently define a release signing configuration, product flavours, Play Store publishing workflow, or a CI release pipeline.

## Build locally

Requirements:
- JDK 17;
- Android SDK Platform 34;
- Gradle wrapper 8.2 (included);
- Android Gradle Plugin 8.2.0 (resolved by Gradle).

Run:

```bash
./gradlew testDebugUnitTest
./gradlew assembleDebug
```

The debug APK is normally generated at:

```text
app/build/outputs/apk/debug/app-debug.apk
```

On Windows use `gradlew.bat`.

## GitHub Actions artifact

The Android CI workflow runs JVM unit tests, builds the debug APK, and uploads the APK as a short-lived workflow artifact. This artifact is for build verification and manual testing. It is not a signed Play Store release.

## Install and test

Install the APK on a compatible device or emulator with API 26 or newer. Test:
- fresh installation;
- create/edit/rename/delete;
- share chooser behaviour;
- process restart and note retention;
- light/dark theme;
- keyboard visibility and back navigation;
- data backup/restore behaviour for the target Android version.

## Release signing

A release build must use a protected signing key. Do not commit:
- keystore files;
- key passwords;
- signing properties containing secrets;
- service account credentials.

Use protected CI secrets or a local keystore outside the repository. Keep backup access to the release signing key under a documented and restricted operational policy.

## Release readiness checks

Before distribution:
1. Run JVM tests and instrumentation tests.
2. Run Android lint and review all findings.
3. Test database migration and data retention.
4. Review backup/data-extraction policy.
5. Review privacy disclosures, especially note contents and sharing.
6. Confirm application ID, version code, version name and icon.
7. Build a signed release APK or Android App Bundle.
8. Test installation and upgrade from the previous public version.
9. Verify existing notes remain readable after upgrade.
10. Test on supported Android versions and at least one small and one large screen.

## Versioning

The current module uses application ID `com.makuta.simplenotes`, version code 1 and version name 1.0. Increment version code for every distributed update. Use semantic versioning for release communication if desired, but Android versionCode must remain monotonically increasing.

## Not a release guarantee

A successful debug build proves compilation and packaging in that environment. It does not prove Play policy compliance, a privacy/security assessment, device-wide compatibility, migration correctness, or release signing.
