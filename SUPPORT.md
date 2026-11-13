# Support

## Documentation first

- [README](README.md): project scope and quick start.
- [Architecture](docs/ARCHITECTURE.md): component and data flow.
- [Database](docs/DATABASE.md): Room entity and DAO.
- [File Storage](docs/FILE_STORAGE.md): body file behaviour.
- [Privacy and Backup](docs/PRIVACY-AND-BACKUP.md): data retention and Android backup.
- [Troubleshooting](docs/TROUBLESHOOTING.md): setup and runtime problems.
- [Security Policy](SECURITY.md): safe reporting.

## Local validation

```bash
./gradlew testDebugUnitTest
./gradlew assembleDebug
```

Use `./gradlew connectedDebugAndroidTest` when a device or emulator is available.

## Information to include in a bug report

- App version/build type.
- Android OS version and device/emulator model.
- Exact steps to reproduce.
- Expected and actual behaviour.
- Whether the issue began after an app update.
- Sanitized log output without note contents or personal identifiers.

## Data safety

Never attach a database, app data directory, device backup, note text, account credentials, or signing keys to a public issue. Use fictional text to reproduce editor/list behaviour.

## Current support boundaries

The repository has no cloud backup, account recovery, remote synchronization or customer support service. If a note is missing, do not uninstall the app or clear storage while investigating; that can permanently remove remaining local data.
