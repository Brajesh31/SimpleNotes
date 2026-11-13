# Privacy, Backup and Retention

## Data currently stored

SimpleNotes stores:
- note titles in Room;
- a generated filename in Room;
- plain UTF-8 note bodies in app-private internal files.

The current application does not request internet or external-storage permissions in its manifest. No remote API, account, advertising SDK, telemetry service, or analytics integration appears in the current dependency/source set.

## Where data lives

- Database: Room database named `notes`, in app-private storage.
- Note body: `filesDir/<64-character-sha256-filename>`.
- Temporary write: a hidden sibling temporary file is used during replacement and removed after the move.
- Sharing: selected content is passed to another app by Android's ACTION_SEND intent.

## Backup configuration

The manifest declares `android:allowBackup="true"`. This permits Android backup mechanisms subject to platform version, device configuration, account/system policy, and any applicable backup/data-extraction rules. The repository does not define custom `fullBackupContent` or `dataExtractionRules` XML.

Therefore, this project makes no guarantee that data is always backed up or always excluded from backup. Validate the actual behaviour on target Android versions before distribution.

## Retention

App-private data normally survives:
- app process termination;
- device restart;
- an in-place app update that retains the same application ID and compatible storage schema.

App-private data is normally removed by:
- uninstalling the app;
- clearing the app's storage/data through Android settings;
- certain device reset or restore procedures.

The application does not provide a formal export, import, archive, recycle bin, or cloud recovery feature. Users should copy important notes elsewhere before uninstalling or clearing data.

## Sharing and third-party destinations

Sharing sends note title and text to the selected Android app. That app may store, transmit, synchronize, or retain the content under its own policy. The current UI uses the standard chooser and does not control downstream retention.

## Privacy guidance for contributors

- Never add real note contents to test fixtures, screenshots, logs or issue descriptions.
- Use fictional content in automated tests.
- Do not add tracking or analytics without a clear disclosure and purpose.
- Minimize permissions and dependencies.
- Do not log note bodies.
- Make export/backup scope explicit to users.
- Provide a clear path to delete stored content if new backup or synchronization features are introduced.

## Encryption status

The current code does not encrypt note body files or the Room database itself. Android app sandboxing and device-level encryption are helpful platform protections, but this app does not implement a separate application-level encryption scheme. Do not describe the app as an encrypted notes vault.
