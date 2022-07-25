package com.example.shipshapenotes;

import static android.widget.Toast.*;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ViewNoteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_note_activity);
        TextView titleTxt = findViewById(R.id.viewNote);
        setupButtonEdit();


        TextView descriptionTxt = findViewById(R.id.viewDescription);
        TextView dateFinalTxt = findViewById(R.id.viewDateInitial);
        TextView dateInitialTxt = findViewById(R.id.viewDateFinal);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
           String title = (String) extras.getString("title");
           String description = (String) extras.getString("description");
           String dataInitial = (String) extras.getString("initialDate");
           String dataFinal = (String) extras.getString("finalDate");


           titleTxt.setText(title);
           descriptionTxt.setText(description);
           dateFinalTxt.setText(dataFinal);
           dateInitialTxt.setText(dataInitial);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note_menu,menu);
        return true;
    }

    private void setupButtonEdit() {
        Button add = findViewById(R.id.editNoteButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    String title = (String) extras.getString("title");
                    String description = (String) extras.getString("description");
                    String dataInitial = (String) extras.getString("initialDate");
                    String dataFinal = (String) extras.getString("finalDate");
                    String position = (String) extras.getString("position");
                    Log.d("TAG", "position in save button" + position);

                    Intent intent = new Intent(ViewNoteActivity.this, AddEditNoteActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("description", description);
                    intent.putExtra("position", position);
                    intent.putExtra("initialDate", dataInitial);
                    intent.putExtra("finalDate", dataFinal);
                    startActivityForResult(intent, 2);
                }
            }
        });
    }







}


