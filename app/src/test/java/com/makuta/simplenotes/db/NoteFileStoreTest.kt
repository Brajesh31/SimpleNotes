package com.makuta.simplenotes.db

import java.io.File
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class NoteFileStoreTest {

    @get:Rule
    val temporaryFolder = TemporaryFolder()

    private val filename = "0123456789abcdef".repeat(4)

    @Test
    fun writesAndReadsUtf8NoteContent() {
        val directory = temporaryFolder.newFolder("notes")
        val store = NoteFileStore(directory)

        store.write(filename, "A note with symbols: café, नमस्ते, and ☀")
        assertEquals("A note with symbols: café, नमस्ते, and ☀", store.read(filename))
    }

    @Test
    fun overwritesExistingContentWithoutLeavingTemporaryFile() {
        val directory = temporaryFolder.newFolder("notes")
        val store = NoteFileStore(directory)

        store.write(filename, "old value")
        store.write(filename, "new value")

        assertEquals("new value", store.read(filename))
        assertEquals(listOf(filename), directory.listFiles()?.map { it.name })
    }

    @Test
    fun returnsNullWhenNoteFileDoesNotExist() {
        val store = NoteFileStore(temporaryFolder.newFolder("notes"))
        assertNull(store.read(filename))
    }

    @Test
    fun deletesContentAndTreatsRepeatedDeletionAsSuccess() {
        val store = NoteFileStore(temporaryFolder.newFolder("notes"))
        store.write(filename, "body")

        assertTrue(store.delete(filename))
        assertTrue(store.delete(filename))
        assertNull(store.read(filename))
    }

    @Test
    fun refusesPathTraversalAndNonHashFilenames() {
        val store = NoteFileStore(temporaryFolder.newFolder("notes"))

        listOf("../outside", "/tmp/outside", "short-name", "g".repeat(64)).forEach { invalid ->
            try {
                store.read(invalid)
                throw AssertionError("Expected invalid filename to be rejected: $invalid")
            } catch (_: IllegalArgumentException) {
                // The filename validator rejected the unsafe input.
            }
        }
    }

    @Test
    fun createsMissingStorageDirectoryBeforeWriting() {
        val directory = File(temporaryFolder.root, "not-created-yet")
        val store = NoteFileStore(directory)

        store.write(filename, "body")

        assertTrue(directory.isDirectory)
        assertEquals("body", store.read(filename))
    }
}
