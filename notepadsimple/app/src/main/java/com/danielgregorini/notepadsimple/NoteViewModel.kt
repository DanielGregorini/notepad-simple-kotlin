package com.danielgregorini.notepadsimple

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.danielgregorini.notepadsimple.data.Note
import com.danielgregorini.notepadsimple.data.NoteDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val noteDao = NoteDatabase.getDatabase(application).noteDao()
    val allNotes: Flow<List<Note>> = noteDao.getAllNotes()

    private val _currentNote = MutableStateFlow<Note?>(null)
    val currentNote: StateFlow<Note?> = _currentNote

    fun insert(title: String, content: String) = viewModelScope.launch {
        noteDao.insert(Note(title = title, content = content))
    }

    fun update(note: Note) = viewModelScope.launch {
        noteDao.update(note)
    }

    fun delete(note: Note) = viewModelScope.launch {
        noteDao.delete(note)
    }

    fun loadNote(noteId: Int) = viewModelScope.launch {
        _currentNote.value = noteDao.getNoteById(noteId)
    }
}