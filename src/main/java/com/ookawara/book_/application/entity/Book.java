package com.ookawara.book_.application.entity;

import java.time.LocalDate;

public class Book {
    private int books_id;
    private String name;
    private LocalDate release_date;
    private boolean is_purchased;
    private int category_id;

    public Book(int books_id, String name, LocalDate release_date, boolean is_purchased, int category_id) {
        this.books_id = books_id;
        this.name = name;
        this.release_date = release_date;
        this.is_purchased = is_purchased;
        this.category_id = category_id;
    }

    public int getBooks_id() {
        return books_id;
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
