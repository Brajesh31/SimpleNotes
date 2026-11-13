# Dependency and Build Tool Inventory

## Build tooling

| Tool | Declared version | Purpose |
|---|---:|---|
| Gradle wrapper | 8.2 | Reproducible Gradle invocation |
| Android Gradle Plugin | 8.2.0 | Android build tasks |
| Kotlin Gradle plugin | 1.9.22 | Kotlin compilation |
| KSP | 1.9.22-1.0.17 | Room compiler processing |
| JDK | 17 in CI | Gradle/AGP runtime |

## Application libraries

| Dependency | Declared version | Responsibility |
|---|---:|---|
| AndroidX Core KTX | 1.12.0 | Android/Kotlin extensions |
| AppCompat | 1.6.1 | Activity and compatibility support |
| ConstraintLayout | 2.1.4 | XML layout constraints |
| Navigation Fragment KTX | 2.7.6 | Fragment navigation |
| Navigation UI KTX | 2.7.6 | Navigation UI integration |
| Material Components | 1.11.0 | Material widgets and themes |
| Lifecycle ViewModel KTX | 2.7.0 | ViewModel coroutine scope |
| Lifecycle LiveData KTX | 2.7.0 | Observable UI state |
| Lifecycle Common Java 8 | 2.7.0 | Lifecycle Java 8 integration |
| Room KTX/runtime | 2.6.1 | Local metadata database and coroutine DAO |
| Room compiler via KSP | 2.6.1 | Room generated implementation |

## Test libraries

- JUnit 4.13.2 for local JVM tests.
- AndroidX Test JUnit 1.1.5 for device tests.
- Espresso Core 3.5.1 for UI/instrumentation test support.
- AndroidX Test Runner via the configured AndroidJUnitRunner.

## Room compiler configuration

KSP is applied at the app module. Room compiler is configured through `ksp("androidx.room:room-compiler:2.6.1")`. The redundant Java annotation processor declaration has been removed so Kotlin Room processing has one configured path.

## Upgrade policy

- Keep Android Gradle Plugin and Gradle wrapper compatible.
- Keep Kotlin and KSP versions aligned.
- Keep Room runtime and compiler versions identical.
- Review AndroidX and Material transitive changes.
- Run JVM tests and debug assembly for every upgrade.
- Run instrumentation tests on an emulator/device for persistence and UI dependency changes.
- Read release notes and migration notes before updating the Android toolchain.

## Dependency security

CI may report dependency warnings or vulnerabilities through Gradle/plugin tooling. Review advisories and dependency paths before upgrading. Do not force major-version upgrades without building and testing. Keep the Gradle wrapper distribution pinned and do not commit locally modified wrapper binaries unless the official wrapper update process is used.
