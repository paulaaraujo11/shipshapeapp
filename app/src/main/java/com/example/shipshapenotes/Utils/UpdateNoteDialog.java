package com.example.shipshapenotes.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.shipshapenotes.Model.Note;
import com.example.shipshapenotes.R;

public class UpdateNoteDialog extends AppCompatDialogFragment {

    private EditText mTitle;
    private EditText mDescription;
    private EditText mDateInitial;
    private EditText mDateFinal;
    private Button mSaveBtn;
    private UpdateNoteDialog.UpdateNoteListener mListener;
    private Note note;
    private static final String TAG = UpdateNoteDialog.class.getSimpleName();

    public void setNote(Note note) {
        this.note = note;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.note_dialog, null);

        builder.setView(view);
        builder.setCancelable(true);
        builder.setTitle("Editar Lembrete");

        mTitle = view.findViewById(R.id.et_title);
        mDescription = view.findViewById(R.id.et_description);
        mDateInitial = view.findViewById(R.id.et_dateinitial);
        mDateFinal = view.findViewById(R.id.et_datefinal);
        mSaveBtn = view.findViewById(R.id.btn_save);

        mTitle.setText(note.getTitle());
        mDescription.setText(note.getDescription());
        mDateInitial.setText(note.getInitialDate());
        mDateFinal.setText(note.getFinalDate());

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTitle.getText().toString();
                String description = mDescription.getText().toString();
                String dateInitial = mDateInitial.getText().toString();
                String dateFinal = mDateFinal.getText().toString();

                if (title.isEmpty() || description.isEmpty() || dateFinal.isEmpty() || dateInitial.isEmpty()) {
                    return;
                } else if (isInvalidateDate(dateInitial, dateFinal)) {
                    return;
                } else {
                    Note currentNote = new Note(title, description, dateInitial, dateFinal);
                    currentNote.setId(note.getId());
                    Log.d(TAG, "Atualizando nota: ID" + note.getId()
                            + " Título:" + title + ","
                            + "Descricao" + description + ","
                            + "Data Inicial" + dateInitial + ","
                            + "Data Final" + dateFinal);
                    mListener.updateNewNote(currentNote);
                    dismiss();
                }
            }
        });

        return builder.create();
    }

    private boolean isInvalidateDate(String dateInitial, String dateFinal) {
        if (Note.isADate(dateInitial) && Note.isADate(dateFinal)) {
            if (Note.validateDate(dateFinal, dateInitial)) {
                return false;
            } else {
                Toast.makeText(getContext(),
                                "A data final deve ser maior que a inicial",
                                Toast.LENGTH_LONG)
                        .show();
                return true;
            }
        } else {
            Toast.makeText(getContext(),
                            "A data final deve ser maior que a inicial",
                            Toast.LENGTH_LONG)
                    .show();
            return true;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (UpdateNoteDialog.UpdateNoteListener) context;
    }

    public interface UpdateNoteListener {
        void updateNewNote(Note note);
    }
}
