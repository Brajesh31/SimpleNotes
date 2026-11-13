# Architecture

```mermaid
flowchart TD
  Activity[MainActivity] --> Nav[Navigation Component]
  Nav --> Main[Main fragment]
  Nav --> Editor[Editor fragment]
  Main --> MainVM[MainViewModel]
  Editor --> EditorVM[EditorViewModel]
  MainVM --> DAO[NotesDAO]
  EditorVM --> DAO
  DAO --> Room[NotesDB / Room]
  MainVM --> Adapter[NotesAdapter]
  Adapter --> Recycler[RecyclerView]
  EditorVM --> Files[App internal files directory]
  Files --> NoteText[Note content files]
  Room --> Metadata[Note metadata and filename]
```

## Main components
- `MainActivity.kt`: hosts the navigation UI.
- `FMain.kt` and `MainVM.kt`: notes list and list state.
- `FEditor.kt` and `EditorVM.kt`: note editing and persistence behavior.
- `Note.kt`, `NotesDAO.kt`, and `NotesDB.kt`: Room persistence.
- `NotesAdapter.kt`: RecyclerView rendering and actions.
- `NotesApp.kt`: application-level setup and shared constants.

The current storage design separates note text files from Room metadata. Keep both sides consistent when renaming or deleting notes.
