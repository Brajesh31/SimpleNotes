package com.makuta.simplenotes.fragment.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makuta.simplenotes.NotesApp
import com.makuta.simplenotes.db.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class SharedNote(
    val title: String,
    val content: String
)

class MainVM : ViewModel() {

    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>> = _notes

    private val _sharedNote = MutableLiveData<SharedNote?>()
    val sharedNote: LiveData<SharedNote?> = _sharedNote

    private val _shareError = MutableLiveData<Boolean>()
    val shareError: LiveData<Boolean> = _shareError

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            refreshNotes()
        }
    }

    fun delete(filename: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val dao = NotesApp.db.notes()
            val note = dao.pullByFilename(filename)
            if (note != null) {
                dao.del(note)
                // A file may be absent after manual cleanup or an earlier
                // partial failure. File deletion is intentionally idempotent.
                runCatching { NotesApp.instance.noteFiles.delete(note.filename) }
            }
            refreshNotes()
        }
    }

    fun rename(filename: String, newName: String) {
        val title = newName.trim()
        if (title.isEmpty()) return

        viewModelScope.launch(Dispatchers.IO) {
            val dao = NotesApp.db.notes()
            val note = dao.pullByFilename(filename)
            if (note != null) {
                note.title = title
                dao.upd(note)
            }
            refreshNotes()
        }
    }

    fun share(filename: String) {
        _sharedNote.value = null
        viewModelScope.launch(Dispatchers.IO) {
            val note = NotesApp.db.notes().pullByFilename(filename)
            val content = note?.let {
                runCatching { NotesApp.instance.noteFiles.read(it.filename) }.getOrNull()
            }
            if (note != null && content != null) {
                _sharedNote.postValue(SharedNote(note.title, content))
                _shareError.postValue(false)
            } else {
                _shareError.postValue(true)
            }
        }
    }

    fun consumeSharedNote() {
        _sharedNote.value = null
    }

    private suspend fun refreshNotes() {
        _notes.postValue(NotesApp.db.notes().all())
    }
}
