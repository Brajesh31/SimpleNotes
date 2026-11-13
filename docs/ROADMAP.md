# Roadmap

This roadmap separates confirmed current behaviour from proposed work. Unchecked items are not implemented.

## Phase 1: Storage correctness and testability
- [x] Use filename as stable note identifier instead of editable title.
- [x] Validate body-file names before path resolution.
- [x] Encapsulate note-body I/O in NoteFileStore.
- [x] Run routine reads and writes on IO dispatchers.
- [x] Remove note file during delete flow.
- [x] Clean up a new body file if metadata insertion fails.
- [x] Write through a temporary file before replacement.
- [x] Add local tests for UTF-8, overwrite, delete and unsafe filenames.
- [x] Fix RecyclerView notification when replacing the full list.
- [x] Replace unresolved root .gitignore conflict markers.

## Phase 2: User-facing reliability
- [ ] Add deletion confirmation and undo.
- [ ] Add explicit save progress and disable duplicate Save taps during active writes.
- [ ] Add clear recovery UI for missing note files.
- [ ] Add note export/import using Android document picker.
- [ ] Add note count and storage usage indicators.
- [ ] Add configurable sort order and modified timestamp.

## Phase 3: Persistence recovery
- [ ] Add startup reconciliation for missing bodies and orphan files.
- [ ] Add in-memory Room database tests and populated-database migration tests.
- [ ] Add a durable operation journal if crash-safe cross-store consistency is required.
- [ ] Add backup/data-extraction rules aligned with a documented privacy choice.
- [ ] Define and test database migration policy before schema changes.

## Phase 4: Privacy and security
- [ ] Create a threat model for local note contents and shared-device risks.
- [ ] Decide whether app-level encryption is required.
- [ ] Design key management and recovery before implementing encryption.
- [ ] Add optional biometric/device credential access if justified.
- [ ] Consider recent-app screenshot privacy and screen capture policy.
- [ ] Document secure deletion limitations accurately.

## Phase 5: Product capability
- [ ] Add search and tags.
- [ ] Add optional folders or categories.
- [ ] Add formatted text only after choosing a stable content model and migration strategy.
- [ ] Add attachments with explicit size/type/storage rules.
- [ ] Add synchronization only after conflict-resolution and identity semantics are designed.

## Release gate

Do not market the app as encrypted, end-to-end private, automatically backed up, or cloud-synchronized until the corresponding features have been implemented, independently reviewed and tested.
