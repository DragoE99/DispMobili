package com.esercitazione.mtodolist;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

class TodoItem {
    private String todo;
    private GregorianCalendar createOn;

    public TodoItem(String todo){
        super();
        this.todo = todo;
        this.createOn = new GregorianCalendar();

    }
    @Override
    public String toString(){
        String currentDate = new SimpleDateFormat("dd/MM/yyyy",
                Locale.ITALIAN).format(createOn.getTime());
        return currentDate + ":\n>> "+ todo;
    }
}
