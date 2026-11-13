package com.makuta.simplenotes.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class NoteFileStoreInstrumentedTest {

    @Test
    fun writesReadsOverwritesAndDeletesPrivateNoteContent() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val directory = File(context.cacheDir, "note-file-store-test").apply {
            deleteRecursively()
            mkdirs()
        }
        val store = NoteFileStore(directory)
        val filename = "a".repeat(64)

        try {
            store.write(filename, "first body")
            assertEquals("first body", store.read(filename))

            store.write(filename, "updated body")
            assertEquals("updated body", store.read(filename))

            assertTrue(store.delete(filename))
            assertNull(store.read(filename))
            assertTrue(store.delete(filename))
        } finally {
            directory.deleteRecursively()
        }
    }

    @Test
    fun rejectsFilenameThatCouldEscapeThePrivateDirectory() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val store = NoteFileStore(File(context.cacheDir, "note-file-store-validation"))

        try {
            store.read("../outside")
            throw AssertionError("A path traversal filename should be rejected")
        } catch (_: IllegalArgumentException) {
            // Expected: only SHA-256 hexadecimal filenames are accepted.
        }
    }
}
