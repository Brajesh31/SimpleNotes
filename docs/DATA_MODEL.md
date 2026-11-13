# Data Model

## Room entity

The `Note` Kotlin data class maps to the `notes` Room table.

| Property | Kotlin type | Storage meaning |
|---|---|---|
| `uid` | `Int?` | Auto-generated Room primary key |
| `title` | `String` | Editable human-readable title |
| `filename` | `String` | Stable reference to the note-body file |

The `uid` is assigned by Room. The current code does not expose it to the UI. The filename is generated as the SHA-256 hex digest of a high-resolution time value and must match the 64-character lowercase hexadecimal format enforced by `NoteFileStore`.

## File record

There is one body file per note. Its path is:

```text
<application filesDir>/<Note.filename>
```

The contents are plain UTF-8 text. The filename has no extension. No separate JSON metadata or header is embedded in the body.

## Identity rules

- `filename` is used as the stable application-level note identifier.
- `title` is editable presentation data and is not guaranteed unique.
- `uid` is the Room row key and determines list ordering.
- The app must not use a title as a stable key for navigation or storage lookups.

This separation allows two different notes to have the same visible title without causing the editor to load or update the wrong row.

## Derived values and absent fields

There is no stored last-modified timestamp, word count, category, colour, pin state, archive state, tag list, attachment list, sync identifier, version, or deletion marker. The list is ordered by descending database ID, which roughly corresponds to insertion order, not last edit time.

## Example conceptual record

```text
Room row:
  uid      = 17
  title    = "Meeting notes"
  filename = "64 lowercase hexadecimal characters"

Private file:
  filesDir/<filename>
  body = "Discuss the project milestones..."
```

## Money, binary data and other unrelated fields

The model contains no monetary values and no binary attachments. Do not overload the body-file field to store arbitrary file paths. If attachments are introduced, define a separate attachment entity with safe internal identifiers, MIME type, size limits, lifecycle, export semantics, and cleanup rules.

## Future schema changes

Any change to entity columns, indexes, constraints, or table names must increment the Room database version and ship an explicit migration. Existing user note metadata must not be discarded by destructive migration. See [Database Contract](DATABASE.md).
