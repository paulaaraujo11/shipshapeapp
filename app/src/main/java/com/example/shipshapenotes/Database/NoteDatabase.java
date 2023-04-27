package com.example.shipshapenotes.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.shipshapenotes.Database.Dao.NoteDao;
import com.example.shipshapenotes.Model.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            //cria nova base caso a instância seja nula
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class,
                            "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();

        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private NoteDao noteDao;

        public PopulateDbAsyncTask(NoteDatabase db) {
            this.noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Aniversário de Gael", "Bar da Cuca", "03/05/23", "03/05/2023"
            ));
            noteDao.insert(new Note("Estudar para Semana de Prova", "Entregar trabalhos e " +
                    "atividades, fazer resumos", "02/05" +
                    "/2023", "10/05/2023"));
            noteDao.insert(new Note("Pagar as contas", "pagar água, luz e boleto do cartão", "10" +
                    "/05/2023",
                    "15/05/2023"));
            return null;
        }
    }

}
