package com.makuta.simplenotes.fragment.editor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makuta.simplenotes.NotesApp
import com.makuta.simplenotes.Utils.sha256
import com.makuta.simplenotes.db.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditorVM : ViewModel() {

    private val _note = MutableLiveData<Note?>()
    val note: LiveData<Note?> = _note

    private val _content = MutableLiveData<String?>()
    val content: LiveData<String?> = _content

    private val _loadError = MutableLiveData<Boolean>()
    val loadError: LiveData<Boolean> = _loadError

    private val _operation = MutableLiveData<Boolean>()
    val operation: LiveData<Boolean> = _operation

    fun load(filename: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val note = NotesApp.db.notes().pullByFilename(filename)
            val body = note?.let {
                runCatching { NotesApp.instance.noteFiles.read(it.filename) }.getOrNull()
            }
            _note.postValue(note)
            _content.postValue(body)
            _loadError.postValue(note == null || body == null)
        }
    }

    fun update(filename: String, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val success = runCatching {
                NotesApp.instance.noteFiles.write(filename, content)
            }.isSuccess
            _operation.postValue(success)
        }
    }

    fun create(title: String, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val filename = System.nanoTime().toString().sha256()
            val note = Note(title.trim(), filename)
            val success = runCatching {
                NotesApp.instance.noteFiles.write(filename, content)
                NotesApp.db.notes().put(note)
            }.isSuccess

            if (!success) {
                runCatching { NotesApp.instance.noteFiles.delete(filename) }
            }
            _operation.postValue(success)
        }
    }
}
