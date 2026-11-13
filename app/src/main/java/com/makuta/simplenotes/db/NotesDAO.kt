package com.makuta.simplenotes.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotesDAO {

    @Query("SELECT * FROM notes ORDER BY uid DESC")
    suspend fun all(): List<Note>

    @Query("SELECT * FROM notes WHERE filename = :filename LIMIT 1")
    suspend fun pullByFilename(filename: String): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun put(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upd(note: Note)

    @Delete
    suspend fun del(note: Note)

    @Query("SELECT COUNT(*) FROM notes")
    suspend fun size(): Int
}
