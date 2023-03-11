package com.example.shipshapenotes.Model;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity(tableName = "Note_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title = "Nova nota";
    private String initialDate;
    private String finalDate;
    private String description;
    private static Integer total = 0;

    //Uso de Composição
    //private List<Task> tasks = new ArrayList<>();

    //Construtores
    public Note(String title, Date initialDate, Date finalDate, String observation) {
        this.title = title;
        this.initialDate = initialDate.toString();
        this.finalDate = finalDate.toString();
        this.description = observation;
       // this.tasks = tasks;
        total +=1;
    }

    public Note(String title) {
        this.title = title;
        total += 1;
    }

    //Getters e Setters
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        if(this.description != null){
            return description;
        }
        return "";
    }
    public void setDescription(String description) {

        this.description = description;
    }
    public String getInitialDate() {
        if(this.initialDate!=null){
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            return formato.format(initialDate);
        }
        return "sem data";
    }

    public void setInitialDate(String initialDate){
        this.initialDate = initialDate;
    }

    public String getFinalDate() {
        if(this.finalDate!=null){
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            String data = formato.format(finalDate);
            return data;
        }
        return "sem data";

    }

    public void setFinalDate(String finalDate){
        this.finalDate = finalDate;
    }

   // public void addTask(Task task) {
     //   this.tasks.add(task);
   // }

    //public void removeTask(Task task) {
        //tasks.remove(task);
    //}

    public void setDates(Date initial_date,Date final_date) {
        //add verificacao - não permitir data inicial maior que a data final
        Boolean verify = true;
        if(verify) {
            this.finalDate = final_date.toString();
            this.initialDate = initial_date.toString();
        }
    }

    public Integer total() {
        return total;
    }

    public Date parseDate(String date) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.parse(date);
    }
}


