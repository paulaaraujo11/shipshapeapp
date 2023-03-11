package com.example.shipshapenotes.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Note {
    private String title = "Nova nota";
    private Date initialDate;
    private Date finalDate;
    private String description;
    private static Integer total = 0;
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    //Uso de Composição
    private List<Task> tasks = new ArrayList<>();

    //Construtores
    public Note(String title, Date initialDate, Date finalDate, String observation) {
        this.title = title;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.description = observation;
        this.tasks = tasks;
        total +=1;
    }

    public Note(String title) {
        this.title = title;
        total += 1;
    }

    //Getters e Setters
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
            return formato.format(initialDate);
        }
        return "sem data";
    }

    public void setInitialDate(String initialDate){
        try {
            this.initialDate = parseDate(initialDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public String getFinalDate() {
        if(this.finalDate!=null){
            return formato.format(finalDate);
        }
        return "sem data";

    }

    public void setFinalDate(String finalDate){
        try {
            this.finalDate = parseDate(finalDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
    }

    public void setDates(Date initial_date,Date final_date) {
        //add verificacao - não permitir data inicial maior que a data final
        Boolean verify = true;
        if(verify) {
            this.finalDate = final_date;
            this.initialDate = initial_date;
        }
    }

    public Integer total() {
        return total;
    }

    public Date parseDate(String date) throws ParseException {
        return formato.parse(date);
    }
}


