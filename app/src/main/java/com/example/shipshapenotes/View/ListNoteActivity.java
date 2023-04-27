package com.example.shipshapenotes.View;

import android.os.Bundle;

import com.example.shipshapenotes.Model.Note;
import com.example.shipshapenotes.Utils.CreateNoteDialog;
import com.example.shipshapenotes.Utils.UpdateNoteDialog;
import com.example.shipshapenotes.ViewModel.NoteViewModel;
import com.example.shipshapenotes.R;
import com.example.shipshapenotes.Adapter.NoteRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

public class ListNoteActivity extends AppCompatActivity implements
        NoteRecyclerViewAdapter.OnNoteClickListner, CreateNoteDialog.CreateNoteListener,
        UpdateNoteDialog.UpdateNoteListener {

    private NoteViewModel noteViewModel;
    private RecyclerView mRecyclerView;
    private NoteRecyclerViewAdapter noteRecyclerViewAdapter;
    private View parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_note_activity);
        intToolbar();
        intView();

        //Observer para "escutar" as mudanças na lista de notas
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteRecyclerViewAdapter.setNotes(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            //apaga o lembrete quando deslizado para o lado
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(noteRecyclerViewAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                snackBar("Lembrete apagado");
            }
        }).attachToRecyclerView(mRecyclerView);
    }

    private void intView() {
        parent = findViewById(android.R.id.content);
        FloatingActionButton fab = findViewById(R.id.fab);
        mRecyclerView = findViewById(R.id.note_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        //Seta o adapter para o RecyclerView
        noteRecyclerViewAdapter = new NoteRecyclerViewAdapter();
        noteRecyclerViewAdapter.setItemOnClick(this);
        mRecyclerView.setAdapter(noteRecyclerViewAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateNoteDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_note:
                noteViewModel.deleteAllNotes();
                snackBar("Todos lembretes foram apagados");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openCreateNoteDialog() {
        CreateNoteDialog createNoteDialog = new CreateNoteDialog();
        createNoteDialog.show(getSupportFragmentManager(), "create note");
    }

    @Override
    public void saveNewNote(Note note) {
        noteViewModel.insert(note);
        snackBar("Lembrete Salvo");
    }

    @Override
    public void onNoteClick(Note note) {
        Log.d("MainActivity_Log", "" + note.getId());
        openUpdateNoteDialog(note);
    }

    private void openUpdateNoteDialog(Note note) {
        UpdateNoteDialog updateNoteDialog = new UpdateNoteDialog();
        updateNoteDialog.setNote(note);
        updateNoteDialog.show(getSupportFragmentManager(), "Lembrete atualizado");
    }

    @Override
    public void updateNewNote(Note note) {
        noteViewModel.update(note);
        snackBar("Lembrete atualizado");
    }

    private void intToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void snackBar(String message) {
        Snackbar.make(parent, message, Snackbar.LENGTH_SHORT).show();
    }

}



