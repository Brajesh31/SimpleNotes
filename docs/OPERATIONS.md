# Operations and Maintenance

## Runtime profile

SimpleNotes is an offline-capable native Android app. The current repository does not configure server workloads, accounts, cloud services, analytics, crash reporting, or a remote support API.

The main operational state is the pair of Room metadata and note-body files stored inside app-private storage.

## Support diagnostics

When investigating a report, collect only:
- app version and build type;
- Android version and device model;
- reproduction steps;
- whether the issue occurs after a clean install or upgrade;
- sanitized Gradle/logcat errors;
- whether the metadata row or corresponding body file is missing, when the user can safely verify it.

Do not request note body contents, passwords, backup archives, or other sensitive user data.

## Data integrity checks

The application currently has no automated reconciliation operation. When designing one, it should compare:
1. Every Room filename against a valid body file.
2. Every body file against a Room metadata reference.
3. Temporary files left behind after interrupted writes.
4. File readability and UTF-8 decode outcome.

Do not delete unknown/orphan files automatically until a recovery policy is documented; those files may be the only remaining copy of a note after a partial failure.

## Backups and recovery

The manifest allows Android backup, but the repository has no explicit custom backup/data-extraction rule set and no user-facing export. Validate actual backup behaviour on target Android releases. Do not promise users that notes will be restored on a new device.

A production-ready release should include a deliberate export/restore path, integrity checks and a documented retention policy.

## Release operations

- Use the committed Gradle wrapper.
- Use JDK 17 with the current configured AGP.
- Run host unit tests and build the debug artifact.
- Run device instrumentation before release.
- Verify Room migration from the previous released version.
- Protect signing keys and passwords.
- Retain build artifacts with their commit SHA and version metadata.

## Incident handling

For a reported data-loss issue, first stop actions that can overwrite or clear app data. Avoid requesting a reinstall or clearing storage. Preserve any available logs without note text, identify app version and Android version, and investigate whether the database row, body file, or both are missing.

## Maintenance boundaries

This project has no cloud operations, payment handling, customer service platform, or remote account recovery. Do not add an operational claim to the README unless the corresponding feature is implemented.
