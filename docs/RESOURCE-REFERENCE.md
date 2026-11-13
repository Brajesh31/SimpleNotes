# Android Resource Reference

This document describes the checked-in Android XML resource structure and how those resources connect to Kotlin code.

## Layouts

### `a_main.xml`
Activity root. Contains the Material AppBarLayout/Toolbar and NavHostFragment constrained below the toolbar.

### `f_main.xml`
Notes list screen. Contains:
- `list`: RecyclerView used by NotesAdapter;
- `empty`: empty-state container;
- `view_img`: decorative empty-folder illustration;
- `fab_new`: button that opens the editor with an empty filename argument and a descriptive accessibility label.

### `f_edit.xml`
Contains the multiline EditText with ID `content`. FEditor reads and updates this field.

### `i_note.xml`
MaterialCardView used by NotesAdapter. Its `title` TextView displays the note title. The note image is decorative and is excluded from accessibility traversal.

### `d_rename.xml`
TextInputLayout and TextInputEditText with ID `editor`. The same layout is used for new note title entry and renaming an existing note.

## Navigation

`navigation/main_graph.xml` declares:
- start destination `FMain`;
- `FEditor` destination;
- `action_FMain_to_FEditor` action.

The `BUNDLE_FILE` argument is passed from FMain to FEditor. Its value is an empty string for a new note and the stable body filename for an existing note.

## Menus

### Editor menu
`menu_edit.xml` contains Share and Save. FEditor hides Share when the editor is creating a new note without a saved metadata record.

### Note popup menu
`menu_popup.xml` contains Rename, Share and Delete. FMain handles each action using the selected note's filename.

## Strings

User-visible labels and errors belong in `values/strings.xml`. This includes app name, empty state, chooser title, new-note and rename titles, menu labels, hints, dialog labels, and load/save/share errors.

Do not hard-code user-facing error text in fragments. Add a string resource and reference it from Kotlin.

## Themes and colours

Light and dark Material 3 themes are declared separately. API 27+ variants set light/dark navigation bar icon treatment. The core colour resource file contains black, white and launcher background values.

## Drawables

Vector assets provide:
- empty folder illustration;
- note list icon;
- add note;
- delete;
- rename;
- save;
- share;
- adaptive launcher foreground.

Launcher WebP files are split by density. Update all required density variants together when branding is changed.

## Accessibility notes

Decorative `ImageView` objects should not compete with actual labels in TalkBack. Controls must have content descriptions or text labels. Text input fields use hints. Verify the screen in TalkBack rather than assuming XML labels alone guarantee a meaningful focus order.
