package com.example.shipshapenotes.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shipshapenotes.Model.Note;
import com.example.shipshapenotes.R;


import java.util.ArrayList;
import java.util.List;

public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewAdapter.RecyclerViewHolder> {

    private List<Note> notes = new ArrayList<>();
    private OnNoteClickListner onNoteClickListner;

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public void setItemOnClick(OnNoteClickListner onNoteClickListner) {
        this.onNoteClickListner = onNoteClickListner;
    }

    public Note getNoteAt(int position) {
        Note note = notes.get(position);
        note.setId(notes.get(position).getId());
        return note;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item, null);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view, onNoteClickListner);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        Note currentNote = notes.get(position);
        holder.mTitle.setText("Título: " + currentNote.getTitle());
        holder.mDescription.setText("Descrição: " + currentNote.getDescription());
        holder.mDateFinal.setText("Prazo final: " + currentNote.getFinalDate());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitle;
        private TextView mDescription;
        private TextView mDateFinal;
        private OnNoteClickListner mListener;

        public RecyclerViewHolder(@NonNull View itemView, OnNoteClickListner onNoteClickListner) {
            super(itemView);
            this.mListener = onNoteClickListner;
            itemView.setOnClickListener(this);
            mTitle = itemView.findViewById(R.id.note_item_title);
            mDescription = itemView.findViewById(R.id.note_item_description);
            mDateFinal = itemView.findViewById(R.id.note_item_datefinal);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Note currentNote = notes.get(position);
            Note note = new Note(currentNote.getTitle(), currentNote.getDescription(),
                    currentNote.getInitialDate(), currentNote.getFinalDate());
            note.setId(currentNote.getId());
            mListener.onNoteClick(note);
        }
    }

    public interface OnNoteClickListner {
        void onNoteClick(Note note);
    }
}
