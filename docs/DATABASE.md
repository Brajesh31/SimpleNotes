# Room Database Contract

## Database configuration

- Database class: `NotesDB`
- Database name: `notes`
- Entity: `Note`
- DAO: `NotesDAO`
- Room version: 2.6.1
- Current schema version: 1
- Database builder: `Room.databaseBuilder(applicationContext, NotesDB::class.java, "notes")`

The database is created once from `NotesApp.onCreate()` and held for the application process. The DAO functions are suspend functions. ViewModels call them from `Dispatchers.IO`.

## Table contract

The `notes` table is backed by the `Note` entity:
- `uid`: integer primary key, auto-generated;
- `title`: text;
- `filename`: text.

There is no unique index on title. Two notes can display the same title. The filename is the stable lookup key and is generated as a SHA-256 hexadecimal string.

## DAO operations

| Method | SQL/annotation | Purpose |
|---|---|---|
| `all()` | `SELECT * FROM notes ORDER BY uid DESC` | Load all rows newest-ID first |
| `pullByFilename(filename)` | `SELECT * FROM notes WHERE filename = :filename LIMIT 1` | Look up a stable note identity |
| `put(note)` | `@Insert(REPLACE)` | Create metadata row |
| `upd(note)` | `@Update(REPLACE)` | Update title and row metadata |
| `del(note)` | `@Delete` | Remove one row |
| `size()` | `SELECT COUNT(*)` | Count rows |

The `REPLACE` conflict strategy is used by insert/update declarations. The filename generator is expected to avoid collisions; it is not a database uniqueness constraint in the current schema.

## Threading contract

Do not use Room from the main thread. Current ViewModels launch list, lookup, insert, update, delete, and share-preparation operations on `Dispatchers.IO`. Room's KTX integration provides suspend DAO support.

## Schema migration policy

The current database is schema version 1 and the repository does not define migrations yet. This is acceptable only while the entity schema remains unchanged. Before shipping a schema change:
1. Increment the database version.
2. Add an explicit `Migration(oldVersion, newVersion)`.
3. Preserve existing note rows.
4. Add migration tests from a prior schema.
5. Test upgrade on a database containing real note records and matching body files.
6. Never add `fallbackToDestructiveMigration()` for user-created notes.

## Database/filesystem boundary

Room transactions cannot make a file write/delete atomic. Create and delete sequences use best-effort ordering and cleanup. A stronger design should record pending operations or use a repository with startup reconciliation so orphan and missing-body cases can be detected and repaired.

## Testing

Local JVM tests cover the independent `NoteFileStore` utility. Database behavior is not currently exercised by the JVM test suite. A future instrumentation test can build an in-memory Room database and verify insertion, lookup by filename, title update, ordered listing, and deletion.
