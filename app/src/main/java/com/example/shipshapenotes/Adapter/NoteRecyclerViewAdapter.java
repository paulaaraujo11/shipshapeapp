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

public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewAdapter.NoteViewHolder> {

    private ArrayList<Note> notes;
    private RecyclerViewClickListener listener;

    public NoteRecyclerViewAdapter(ArrayList<Note> notes,RecyclerViewClickListener listener){
        this.notes = notes;
        this.listener = listener;
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title;
        private TextView description;
        private TextView data_final;

        public NoteViewHolder(final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.note_item_title);
            description = itemView.findViewById(R.id.note_item_description);
            data_final = itemView.findViewById(R.id.note_item_datefinal);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view,getAdapterPosition());
        }
    }
    public void addNote(Note note) {
        this.notes.add(note);
        notifyDataSetChanged();
    }

    public void updateNote(Note note, int position) {
        this.notes.set(position, note);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteRecyclerViewAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item,parent,false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteRecyclerViewAdapter.NoteViewHolder holder, int position) {
        String title = notes.get(position).getTitle();
        String description = notes.get(position).getDescription();
        String datefinal = notes.get(position).getFinalDate();
        holder.title.setText(title);
        holder.description.setText(description);
        holder.data_final.setText(datefinal);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
}
