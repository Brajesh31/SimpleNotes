# User Flows

## Launch and list

1. Android starts `NotesApp` and initializes Room.
2. `MainActivity` hosts the navigation graph.
3. `FMain` configures the list adapter and empty state.
4. `MainVM.load()` queries the Room table on IO.
5. When metadata arrives, the fragment displays the note list or the empty-state view.

Note body files are not opened while drawing the list. The list displays stored titles only.

## Create a note

1. Tap the floating action button.
2. The navigation graph opens `FEditor` with an empty filename argument.
3. Enter body text in the multiline editor.
4. Select Save.
5. Enter a non-blank title in the Save As dialog.
6. The fragment calls `EditorVM.create(title, content)`.
7. The ViewModel generates a SHA-256 filename, writes the UTF-8 body file, and inserts the Room row on an IO dispatcher.
8. If the operation succeeds, the editor navigates back to the notes list. If it fails, the user sees a save error.

The app allows an empty body to be saved if a title is supplied. The current validation is on the title, not on body length.

## Open an existing note

1. Tap a row in the RecyclerView.
2. The adapter passes the note's filename to the fragment callback.
3. `FMain` places the filename in the navigation bundle.
4. `FEditor` calls `EditorVM.load(filename)`.
5. The ViewModel fetches the Room row by filename and reads the body via NoteFileStore on IO.
6. The fragment updates its toolbar title and editor content when LiveData emits.

## Edit and save

1. Change text in the editor.
2. Select Save.
3. The fragment passes the existing filename and current content to `EditorVM.update()`.
4. NoteFileStore replaces the body file through a temporary sibling file.
5. On success the editor returns to the list; on failure it remains open and shows an error.

The Room row is unchanged during a normal edit because the title and filename do not change.

## Rename

1. Long-press a note row.
2. Select Rename.
3. Enter a non-blank title.
4. MainVM looks up the row by filename and updates only the title.
5. The filename and body file are preserved.

Duplicate visible titles are currently allowed. Each row remains independently addressable by its filename.

## Delete

1. Long-press a note row.
2. Select Delete.
3. MainVM retrieves the metadata record by filename.
4. It deletes the Room row and attempts to delete the body file.
5. The list is reloaded on IO and the empty state is displayed if no records remain.

The deletion UI currently has no confirmation dialog. The underlying file deletion is best effort because Room and the filesystem are separate resources.

## Share from the list

1. Long-press a note row.
2. Select Share.
3. MainVM loads the metadata and body text on IO.
4. A share payload containing title and body is published to the fragment.
5. FMain launches an `ACTION_SEND` chooser with MIME type `text/plain`.

## Share from the editor

For an existing note, the editor toolbar exposes Share. FEditor shares the current text field content, including unsaved edits in the current editor session. The platform chooser decides which installed applications can receive the text.

## Persistence and reinstall

Room metadata and body files are stored within app-private storage. They normally remain across app process restarts and application upgrades. Uninstalling or clearing app data removes the app's local data. Backup behaviour depends on Android settings and the manifest's current allow-backup configuration.
