package com.makuta.simplenotes

import android.app.Application
import androidx.room.Room
import com.makuta.simplenotes.db.NoteFileStore
import com.makuta.simplenotes.db.NotesDB

class NotesApp : Application() {

    companion object {
        const val BUNDLE_FILE = "bundle_file"

        lateinit var db: NotesDB
            private set

        lateinit var instance: NotesApp
            private set
    }

    val noteFiles: NoteFileStore by lazy {
        NoteFileStore(filesDir)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        db = Room.databaseBuilder(applicationContext, NotesDB::class.java, "notes").build()
    }
}
