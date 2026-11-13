# Note File Storage

## Responsibility

`NoteFileStore` is the only abstraction that should read, write, or delete note-body files. It is constructed with the application's internal `filesDir` from `NotesApp.noteFiles`.

## Filename contract

The file name must match:

```regex
^[a-f0-9]{64}$
```

The current generator hashes `System.nanoTime().toString()` with SHA-256. The result is a lowercase hexadecimal string with 64 characters. Validation is not encryption and does not provide content confidentiality. It only restricts the expected path shape and rejects directory traversal input.

## Encoding and file layout

- Encoding: UTF-8.
- One file per note.
- No extension.
- File content: the note text as entered by the user.
- Location: app-private internal files directory.
- No storage permission is requested.

## Write algorithm

1. Validate the filename.
2. Ensure the private notes directory exists.
3. Write content to a temporary sibling file.
4. Move the temporary file over the destination, requesting an atomic replacement.
5. If the filesystem does not support atomic move, retry using a replacement move.
6. Remove the temporary file if the operation fails and propagate the error to the caller.

This reduces the chance that a process interruption leaves a partially overwritten note. It does not make the later Room metadata operation atomic with the filesystem operation.

## Read and delete

- `read(filename)` returns the complete UTF-8 body, or `null` if the target is absent.
- `delete(filename)` returns true when the target is removed or was already absent.
- Invalid names fail validation rather than being interpreted as paths.

I/O exceptions are surfaced to the ViewModel, which converts them into UI state. The fragment should not directly instantiate `File` for note-body operations.

## Security properties and limitations

Internal files are protected by Android's application sandbox against ordinary access by other apps. This is not equivalent to encrypted storage or end-to-end encryption. A rooted or compromised device, debug access, backup extraction, or app compromise may expose content depending on the environment.

The current app does not encrypt note files, use a user-provided key, or offer secure deletion. SHA-256 is used for filename generation, not for hashing the body and not for encrypting it.

## Storage growth

There is no note-size limit, attachment limit, quota, or automatic cleanup policy. Files can grow as users add content. The app does not display storage usage or gracefully recover from all low-storage errors.

## Future improvements

- Add a startup reconciliation report for metadata rows whose files are missing.
- Identify orphan files with no metadata reference and remove them only under a deliberate retention policy.
- Add export/import with explicit user consent.
- Consider an encrypted-storage design with key management and recovery consequences before implementing encryption.
- Add bounded note sizes and robust low-storage messaging.
