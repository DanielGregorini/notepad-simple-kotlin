package com.danielgregorini.notepadsimple

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.danielgregorini.notepadsimple.data.Note
import com.danielgregorini.notepadsimple.screens.NoteDetailScreen
import com.danielgregorini.notepadsimple.screens.NoteListScreen
import com.danielgregorini.notepadsimple.ui.theme.NotepadSimpleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotepadSimpleTheme {
                NoteApp()
            }
        }
    }
}

@Composable
fun NoteApp(viewModel: NoteViewModel = viewModel()) {
    val notes by viewModel.allNotes.collectAsState(initial = emptyList())
    var selectedNote by remember { mutableStateOf<Note?>(null) }
    var showDetail by remember { mutableStateOf(false) }

    if (showDetail) {
        NoteDetailScreen(
            note = selectedNote,
            onBackClick = {
                showDetail = false
                selectedNote = null
            },
            onDeleteClick = { note ->
                viewModel.delete(note)
            },
            onSaveClick = { note ->
                if (note.id == 0) {
                    viewModel.insert(note.title, note.content)
                } else {
                    viewModel.update(note)
                }
            }
        )
    } else {
        NoteListScreen(
            notes = notes,
            onNoteClick = { note ->
                selectedNote = note
                showDetail = true
            },
            onAddClick = {
                selectedNote = null
                showDetail = true
            }
        )
    }
}