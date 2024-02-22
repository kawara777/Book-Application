package com.ookawara.book_.application.Entity;

import java.time.LocalDate;
import java.util.Date;

public class Book {
    private int id;
    private String name;
    private LocalDate release_date;
    private boolean is_purchased;
    private int category_id;

    public Book(int id, String name, LocalDate release_date, boolean is_purchased, int category_id) {
        this.id = id;
        this.name = name;
        this.release_date = release_date;
        this.is_purchased = is_purchased;
        this.category_id = category_id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getRelease_date() {
        return release_date;
    }

    public boolean isIs_purchased() {
        return is_purchased;
    }

    public int getCategory_id() {
        return category_id;
    }
}
