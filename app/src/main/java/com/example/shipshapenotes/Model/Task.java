package com.example.shipshapenotes.Model;

public class Task {
    private String title = "Nova task";
    private Boolean isChecked = false;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void checkTask() {
        if(this.isChecked == false) {
            this.isChecked = true;
        }else {
            this.isChecked = false;
        }
    }
}
