package com.example.a.entidades;

import java.io.Serializable;

public class Objeto implements Serializable {
    private Integer id;
    private String toDo;
    private String date;
    private String complete;

    public Objeto(Integer id, String toDo, String date, String complete) {
        this.id = id;
        this.toDo = toDo;
        this.date = date;
        this.complete = complete;
    }

    public Objeto(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToDo() {
        return toDo;
    }

    public void setToDo(String toDo) {
        this.toDo = toDo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComplete() {
        return complete;
    }

    public String setComplete(String complete) {
        this.complete = complete;
        return complete;
    }
}
