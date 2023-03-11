package com.example.shipshapenotes.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.shipshapenotes.Model.Note;
import com.example.shipshapenotes.Repository.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }


    public static void insert(Note note){
        repository.insert(note);
    }

    public void update(Note note){
        repository.update(note);
    }

    public void delete(Note note){
        repository.delete(note);
    }

    public void deleteAllPlayers(){
        repository.deleteAllPlayers();
    }

    public LiveData<List<Note>> getAllPlayers(){
        return allNotes;
    }
}