# Data Storage

## Overview

SimpleNotes stores a note across two app-private persistence locations:

1. Room database `notes`, which stores the note title, generated body filename, and primary key.
2. A UTF-8 text file in Android's `filesDir`, whose filename is stored in the corresponding Room row.

The body is not stored in a Room text column. The list screen loads metadata only. The editor and share flows read the body through `NoteFileStore`.

## Room metadata

The `Note` entity maps to the `notes` table. It contains:
- `uid`: nullable auto-generated integer primary key;
- `title`: visible title;
- `filename`: stable body-file identifier.

The DAO exposes:
- `all()`: all note entities, ordered by descending database ID;
- `pullByFilename(filename)`: one note identified by its generated file key;
- `put(note)`: insert/replace metadata;
- `upd(note)`: update metadata;
- `del(note)`: delete metadata;
- `size()`: row count.

## Body files

`NoteFileStore` owns file operations. It accepts only a 64-character lowercase hexadecimal filename, which is the format produced by the current SHA-256 filename generator. This validation prevents path separators and traversal strings from being used as a file path.

Files are encoded as UTF-8. Writes first create a temporary file and then move it over the destination, requesting an atomic replacement where supported. Reads return `null` for a missing file. Delete returns success when a file is already absent, making repeated cleanup safe.

## Create flow

1. The user writes note content in `FEditor`.
2. The user chooses Save and enters a non-blank title.
3. `EditorVM.create(title, content)` runs on an IO dispatcher.
4. The ViewModel generates the filename.
5. `NoteFileStore.write()` writes the UTF-8 body.
6. Room inserts the corresponding metadata row.
7. On success, the editor receives a successful operation result and returns to the list.
8. If the operation fails, the ViewModel attempts to remove the created file and reports failure to the editor.

A filesystem write and Room insert are not one atomic transaction. Cleanup is best effort if the second operation fails.

## Edit flow

1. The list passes the note filename to the editor.
2. The DAO finds metadata by filename.
3. The ViewModel reads the associated body on IO.
4. The fragment displays the content.
5. Save writes updated text to the existing filename.
6. The editor returns to the list after a successful write.

Editing does not change the filename or Room metadata.

## Rename flow

Rename updates only the Room title. The filename and note-body file remain unchanged. All navigation and operations use the filename, so renaming no longer changes the identifier used to open or share a note.

The app currently permits duplicate display titles. Filenames remain unique and are used for record identity.

## Delete flow

The ViewModel retrieves the row by filename, deletes the Room record, and attempts to delete the corresponding body file. A missing body file is treated as already deleted. Because the database and filesystem are separate, deletion is not a cross-resource transaction.

## Sharing

The list ViewModel loads both metadata and body on an IO coroutine, then exposes a small share payload (title and content). The fragment launches Android's `ACTION_SEND` chooser. The editor shares its currently displayed text through the same Android chooser mechanism.

Sharing transfers note contents to the selected target application. The user should only share content with recipients and apps they trust.

## Data retention

The Room database and note files are app-private. Uninstalling the app normally removes its private data. The manifest currently sets `android:allowBackup="true"`; actual backup and device-transfer behavior depends on Android version, device settings and applicable system backup policy. The project does not define custom backup/data-extraction rules.

## Consistency limitations

- Room transactions cannot include filesystem writes.
- A process interruption during create/delete can leave an orphan file or a missing body.
- No startup reconciliation job scans the database and files for mismatches.
- No user-facing recovery screen exists for damaged or missing note files.
- No export/restore workflow is implemented.

Important notes should be exported or copied elsewhere before uninstalling, resetting, or changing devices.
