# ViewModel and UI State Contract

## Why ViewModels own the work

Fragments are responsible for rendering and user interaction. Database and filesystem operations belong to ViewModels, which launch work in `viewModelScope` on `Dispatchers.IO`. Results are posted to LiveData and observed while the fragment's view lifecycle is active.

## MainVM outputs

| LiveData | Type | Meaning |
|---|---|---|
| `notes` | `List<Note>` | Current database metadata rows |
| `sharedNote` | `SharedNote?` | One note title/body payload ready for Android sharing |
| `shareError` | `Boolean` | True when metadata/body could not be loaded for sharing |

### MainVM operations
- `load()`: refreshes all rows in descending UID order.
- `delete(filename)`: deletes the matching row and attempts to remove its body file, then refreshes.
- `rename(filename, newName)`: trims and applies a non-empty title, then refreshes.
- `share(filename)`: reads metadata and content on IO and posts a share payload or error.
- `consumeSharedNote()`: clears the share payload after the fragment has consumed it, preventing a stale chooser from reopening on a later observer subscription.

## EditorVM outputs

| LiveData | Type | Meaning |
|---|---|---|
| `note` | `Note?` | Metadata for the selected filename |
| `content` | `String?` | Loaded body text; empty string is a valid empty note |
| `loadError` | `Boolean` | True when metadata or body cannot be loaded |
| `operation` | `Boolean` | True on successful save; false on failure |

### EditorVM operations
- `load(filename)`: queries Room and reads the body file on IO.
- `update(filename, content)`: atomically replaces the existing body through NoteFileStore.
- `create(title, content)`: generates filename, writes body, inserts metadata, and cleans up the new file if metadata insertion fails.

## Fragment lifecycle

Fragments use `viewLifecycleOwner` when observing LiveData. This prevents UI observers from outliving the fragment view. Observers are also removed in `onStop()` in the current code.

The editor's menu provider is registered with the view lifecycle owner. The fragment does not hold a separate background thread or executor.

## Event-like LiveData

The share result is event-like rather than permanent screen state. FMain calls `consumeSharedNote()` immediately after receiving a non-null payload. This avoids reopening an old share chooser when the fragment stops and starts again.

The boolean save operation is also transient in practice. If a future UI adds multiple concurrent operations, replace boolean result fields with a typed operation state or event wrapper to distinguish operation IDs and outcomes.

## Error handling

- Note load failure is displayed to the user.
- Save failure is displayed and the editor remains on screen.
- Share failure is displayed rather than reading a missing file from the main thread.
- Delete file cleanup is best effort; the current ViewModel does not expose a dedicated cleanup error state.

## Future state improvements

Use sealed UI state for loading/ready/error and typed one-shot events for navigation/share. Expose progress and retry actions for large note bodies or storage errors. Keep data access off the main thread.
