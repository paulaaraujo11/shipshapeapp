package com.example.shipshapenotes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddEditNoteActivity extends AppCompatActivity {
    private static final String TAG = "VALUES";
    private EditText mTitle;
    private EditText mDescription;
    private EditText mDateInitial;
    private EditText mDateFinal;
    private String mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addedit_note_activity);

        mTitle = findViewById(R.id.editTitle);
        mDescription = findViewById(R.id.editDescription);
        mDateFinal = findViewById(R.id.editDateFinal);
        mDateInitial = findViewById(R.id.editDateInitial);

        setupButtonAddEdit();
        setupValues();
    }


    private void setupValues() {
        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String dataInitial = intent.getStringExtra("initialDate");
        String dataFinal = intent.getStringExtra("finalDate");
        mPosition = intent.getStringExtra("position");
        if(mPosition==null){
            mPosition = "-1";
        }
        Log.d("TAG", "position in VIEW NOTE" + mPosition);

        mTitle.setText(title);
        mDescription.setText(description);
        mDateFinal.setText(dataFinal);
        mDateInitial.setText(dataInitial);
    }

    private void setupButtonAddEdit(){
        Button add_edit = findViewById(R.id.saveNoteButton);

        add_edit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //get values in form
                String title = mTitle.getText().toString();
                String description = mDescription.getText().toString();
                String dateInitial = mDateInitial.getText().toString();
                String dateFinal = mDateFinal.getText().toString();

                //create a intent to return to ListNoteActivity
                Intent intent = new Intent(AddEditNoteActivity.this,ListNoteActivity.class);
                intent.putExtra("title",title);
                intent.putExtra("description",description);
                intent.putExtra("date_initial",dateInitial);
                intent.putExtra("date_final",dateFinal);
                intent.putExtra("position", mPosition);

                // enviar de volta pra main activity
                setResult(RESULT_OK,intent);
                startActivity(intent);

                //tava usando antes mas não funciona pra editar
                //startActivityForResult(intent,1);
                //finish();
            }
        });
    }





}