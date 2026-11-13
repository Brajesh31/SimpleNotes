package com.makuta.simplenotes.db

import java.io.File
import java.nio.file.AtomicMoveNotSupportedException
import java.nio.file.Files
import java.nio.file.StandardCopyOption

/**
 * Owns note-body files stored in the app's private files directory.
 *
 * Filenames are generated as SHA-256 hex strings by the application. Restricting
 * the accepted format prevents a malformed database value from escaping the
 * private notes directory through path traversal.
 */
class NoteFileStore(private val directory: File) {

    fun read(filename: String): String? {
        val file = resolve(filename)
        return if (file.isFile) file.readText(Charsets.UTF_8) else null
    }

    fun write(filename: String, content: String) {
        val destination = resolve(filename)
        if (!directory.exists() && !directory.mkdirs()) {
            throw IllegalStateException("Unable to create the notes directory")
        }

        val temporary = File(directory, ".$filename.tmp")
        try {
            temporary.writeText(content, Charsets.UTF_8)
            try {
                Files.move(
                    temporary.toPath(),
                    destination.toPath(),
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE
                )
            } catch (_: AtomicMoveNotSupportedException) {
                Files.move(
                    temporary.toPath(),
                    destination.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
                )
            }
        } catch (error: Exception) {
            temporary.delete()
            throw error
        }
    }

    /**
     * Returns true when the file is absent after this call. A missing file is
     * treated as already deleted so metadata cleanup remains idempotent.
     */
    fun delete(filename: String): Boolean {
        val file = resolve(filename)
        return !file.exists() || file.delete()
    }

    private fun resolve(filename: String): File {
        require(FILENAME_PATTERN.matches(filename)) {
            "Invalid note filename"
        }
        return File(directory, filename)
    }

    private companion object {
        val FILENAME_PATTERN = Regex("^[a-f0-9]{64}$")
    }
}
