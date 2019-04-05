package com.esercitazione.mtodolist;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

public class TodoItem {
    private String task;
    private GregorianCalendar date;
    private long id;
    public TodoItem(){

    }

    public TodoItem(String todo){
        super();
        this.task = todo;
        this.date = new GregorianCalendar();

    }
    @Override
    public String toString(){
        String currentDate = new SimpleDateFormat("dd/MM/yyyy",
                Locale.ITALIAN).format(date.getTime());
        return currentDate + ":\n>> "+ task;
    }

    public GregorianCalendar getDate() {
        return date;
    }
    public void setDate(GregorianCalendar d) {
        this.date = d;
    }
    public long getId(){
        return this.id;
    }
    public void setId(long i){
        this.id = i;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}