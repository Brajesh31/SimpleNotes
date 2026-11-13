# Data Storage

## Current model
Room stores note metadata through the `Note` entity and `NotesDAO`. The app stores note body text as files in its internal files directory. A metadata record references the note file by filename.

## Operations
- Creating or editing a note must keep file content and metadata synchronized.
- Renaming a note changes its displayed title and should not orphan the body file.
- Deleting a note should remove both the metadata row and corresponding file.
- Sharing reads the file content and sends it through Android's standard share chooser.

## Schema changes
The Room database is currently version 1. Any schema change must increment the database version and include a migration strategy. Avoid destructive migrations for user notes.

## Backup and retention
App-private files and Room data are app data. Review manifest backup settings and device transfer behavior. Users should export important notes before uninstalling or resetting the device.
