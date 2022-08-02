package com.example.shipshapenotes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class ListNoteActivity extends AppCompatActivity {
    private ArrayList<Note> notes;
    private RecyclerView recyclerView;
    private NoteRecyclerViewAdapter adapter;
    private NoteRecyclerViewAdapter.RecyclerViewClickListener listener;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_note_activity);
        recyclerView = findViewById(R.id.note_list);
        notes = new ArrayList<>();
        setUserNotes();
        setAdapter();
        setupButtonAddNote();

        //Apagar nota com Swipe
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Note deletedNote = notes.get(viewHolder.getAdapterPosition());
                int position = viewHolder.getAdapterPosition();
                notes.remove(position);
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                Snackbar.make(recyclerView, deletedNote.getTitle(), Snackbar.LENGTH_LONG).setAction("Desfazer", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notes.add(position, deletedNote);
                        adapter.notifyItemInserted(position);
                    }
                }).show();
            }
        }).attachToRecyclerView(recyclerView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        saveChangesNotes();
    }

    private void saveChangesNotes() {
        Intent data = getIntent();
        if (data.getExtras() != null) {
            Log.d("TAG", "Dados " + data.toString());
            // get the item note
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("description");
            String date_initial = data.getStringExtra("date_initial");
            String date_final = data.getStringExtra("date_final");

            Note note = new Note(title);
            note.setDescription(description);
            note.setInitialDate(date_initial);
            note.setFinalDate(date_final);
            String sPosition = data.getStringExtra("position");
            Log.d("TAG", "position " + sPosition);
            Integer position = Integer.valueOf(sPosition);
            if (position != -1) {
                adapter.updateNote(note, position);
            } else {
                adapter.addNote(note);
            }
        }
    }

    private void setupButtonAddNote() {
        FloatingActionButton add = findViewById(R.id.addNoteFAB);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListNoteActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void setAdapter() {
        setOnclickListener();
        adapter = new NoteRecyclerViewAdapter(notes, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnclickListener() {
        listener = new NoteRecyclerViewAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), ViewNoteActivity.class);
                intent.putExtra("title", notes.get(position).getTitle());
                intent.putExtra("description", notes.get(position).getDescription());
                intent.putExtra("finalDate", notes.get(position).getFinalDate());
                intent.putExtra("initialDate", notes.get(position).getInitialDate());
                intent.putExtra("position", String.valueOf(position));
                Log.d("TAG", "position in LIST " + position);
                startActivity(intent);
            }
        };
    }
    
    private void setUserNotes() {
        notes.add(new Note("Ir ao mercado"));
        notes.add(new Note("Estudar para as provas", new Date(), new Date(), "Provas trimestrais"));
        notes.add(new Note("Ir na festa da Camila"));
        notes.add(new Note("Marcar médico"));

    }


    //estava usando essa função antes para salvar a nota
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Log.d("TAG", "Dados " +data.toString());
            // get the item note
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("description");
            String date_initial = data.getStringExtra("date_initial");
            String date_final = data.getStringExtra("date_final");

            Note note = new Note(title);
            note.setDescription(description);
            note.setInitialDate(date_initial);
            note.setFinalDate(date_final);

            if (requestCode == 1) {
                adapter.addNote(note);
            } else {
                int position = data.getIntExtra("position", -1);
                adapter.updateNote(note, position);
            }
        }

    }


}
