package com.example.shipshapenotes.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity(tableName = "Note_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String initialDate;
    private String finalDate;
    private String description;
    private static Integer total = 0;

    //Uso de Composição
    //private List<Task> tasks = new ArrayList<>();

    //Construtores
    public Note(String title, String observation, String initialDate, String finalDate) {
        this.title = title;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.description = observation;
        // this.tasks = tasks;
        total += 1;
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
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(String initialDate) {
        this.initialDate = initialDate;
    }

    public String getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(String finalDate) {
        this.finalDate = finalDate;
    }

    /*
    FUNÇÕES ÚTEIS A CLASSE DE NOTAS
     */
    public static boolean isADate(String date) {
        String regex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    public static boolean validateDate(String dateFinal, String dateInitial) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate datef = LocalDate.parse(dateFinal, formatter);
        LocalDate datei = LocalDate.parse(dateInitial, formatter);
        if (datef.isBefore(datei)) {
            return false;
        }
        return true;
    }

    //TO DO - add tasks novas para uma nota/lembrete
    // public void addTask(Task task) {
    //   this.tasks.add(task);
    // }

    //public void removeTask(Task task) {
    //tasks.remove(task);
    //}
}


