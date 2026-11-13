# Component and Resource Reference

This guide maps the application's current source files to their responsibilities. It is based on the checked-in Kotlin and Android resource files.

## Kotlin application entry points

### `MainActivity.kt`
Inflates `AMainBinding`, installs the toolbar as the support action bar, and hosts the Navigation Component graph through the activity layout. It does not own notes or perform persistence.

### `NotesApp.kt`
An Android `Application` subclass. During process startup it stores the application instance and creates the Room database. It also exposes a lazy `NoteFileStore` bound to the application's private `filesDir`. It is process-scoped infrastructure, not a screen-level ViewModel.

### `Utils.kt`
Contains small view visibility extensions, a String-to-Editable helper, and the SHA-256 string extension used to create current note filenames. SHA-256 here is a filename-generation utility, not note encryption.

## Database and storage package

### `db/Note.kt`
Room entity for a note's database ID, display title, and body filename. The title can change; the filename is the stable storage identity.

### `db/NotesDAO.kt`
Suspend DAO operations to list rows, look up one row by filename, insert, update, delete and count notes. List ordering is descending by Room's generated UID.

### `db/NotesDB.kt`
Room database declaration. It currently contains the `Note` entity and is at schema version 1.

### `db/NoteFileStore.kt`
Encapsulates UTF-8 file read/write/delete operations. It validates SHA-256-shaped filenames, writes through a temporary file, and confines operations to its supplied private directory.

## List feature

### `fragment/main/MainVM.kt`
Loads note metadata, renames/deletes rows, and reads body content for the Android share chooser. Database and file operations are launched on an IO dispatcher. It exposes notes and one-time share result state via LiveData.

### `fragment/main/FMain.kt`
Configures RecyclerView, empty state and new-note navigation. It displays popup actions and launches Android's share chooser using a payload prepared by MainVM. It does not read note files itself.

### `adapter/NotesAdapter.kt`
Renders `Note` objects in the RecyclerView. The title is displayed, while the filename is used for stable identity and callbacks. Replacing the complete list triggers a full data-set notification so newly inserted rows are shown even when the old list was empty.

## Editor feature

### `fragment/editor/EditorVM.kt`
Loads metadata and body text from IO, saves edits, and creates new note file/metadata pairs. It reports load and save results to the fragment through LiveData.

### `fragment/editor/FEditor.kt`
Owns the multiline text input, Save/Share menu and Save As dialog. It passes a stable filename to the ViewModel when editing and sends content to the ViewModel instead of writing files from the fragment.

## Activity and navigation resources

| Resource | Responsibility |
|---|---|
| `res/layout/a_main.xml` | Activity root, Material toolbar and NavHostFragment |
| `res/navigation/main_graph.xml` | Start destination and list-to-editor navigation action |
| `res/layout/f_main.xml` | Notes RecyclerView, empty state and new-note FAB |
| `res/layout/f_edit.xml` | Multiline note content input |
| `res/layout/i_note.xml` | Material card used by one adapter row |
| `res/layout/d_rename.xml` | Reusable title-entry dialog layout |

## Menu resources

- `res/menu/menu_edit.xml`: Save and Share actions in the editor.
- `res/menu/menu_popup.xml`: Rename, Share and Delete actions for a list item.

Menu items use string resources for labels.

## Values and themes

- `res/values/strings.xml`: app label, hints, menu labels, dialog text, and operation error messages.
- `res/values/colors.xml`: base black, white and launcher background colour.
- `res/values/themes.xml`: Material 3 light theme.
- `res/values-night/themes.xml`: Material 3 dark theme.
- `res/values-v27/themes.xml`: light system bar treatment for API 27+.
- `res/values-night-v27/themes.xml`: dark system bar treatment for API 27+.

## Drawable and launcher resources

Drawable XML files provide the empty-folder illustration, note card glyph, and add/delete/rename/save/share action icons. Launcher resources are separated by density and adaptive icon XML. See [Asset Inventory](ASSET-INVENTORY.md) for the complete tracked resource list.

## Testing source sets

- `app/src/test/`: JVM tests that run on the build host, including NoteFileStore tests.
- `app/src/androidTest/`: tests that require an Android device or emulator, including the app context check and instrumented file-store checks.
