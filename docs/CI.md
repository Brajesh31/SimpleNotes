# Continuous Integration

## Android CI workflow

The repository's Android CI workflow runs for:
- pushes to `main`;
- pull requests targeting `main`;
- manual workflow dispatch.

The job runs on Ubuntu with JDK 17 and the committed Gradle wrapper. It uses Gradle's setup action for dependency/build caching.

## Verification steps

1. Check out the source commit.
2. Install/configure JDK 17.
3. Set up Gradle and cache Gradle dependencies.
4. Run `./gradlew testDebugUnitTest`.
5. Run `./gradlew assembleDebug`.
6. Upload `app-debug.apk` as a seven-day workflow artifact.

The artifact is useful for quick manual install testing. It is not a signed release artifact.

## Why instrumentation tests are separate

The current CI job does not launch an Android emulator. Device tests under `app/src/androidTest/` require a device/emulator and can be run locally with `./gradlew connectedDebugAndroidTest`. A future CI change may add emulator provisioning if runtime and stability are justified.

## Re-running a failure

Open the failed GitHub Actions run and inspect the first failed Gradle task. Reproduce locally with the same task and JDK:

```bash
./gradlew testDebugUnitTest --stacktrace
./gradlew assembleDebug --stacktrace
```

Do not treat the final `BUILD FAILED` line as the root cause. Use the earliest Kotlin, resource, dependency, SDK or task error above it.

## Permissions

The workflow requests read-only repository contents permission. The build does not need write access to source branches. The debug APK is uploaded as a workflow artifact.

## Release gate

A green debug CI run does not replace Android instrumentation, manual device checks, migration testing, privacy review, backup testing, or release signing verification.
