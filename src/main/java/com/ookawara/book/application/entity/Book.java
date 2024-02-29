package com.ookawara.book.application.entity;

import java.time.LocalDate;

public class Book {
    private int book_id;
    private String name;
    private LocalDate release_date;
    private boolean is_purchased;
    private int category_id;

    public Book(int book_id, String name, LocalDate release_date, boolean is_purchased, int category_id) {
        this.book_id = book_id;
        this.name = name;
        this.release_date = release_date;
        this.is_purchased = is_purchased;
        this.category_id = category_id;
    }

    public int getBookId() {
        return book_id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getReleaseDate() {
        return release_date;
    }

    public boolean isIsPurchased() {
        return is_purchased;
    }

    public int getCategoryId() {
        return category_id;
    }
}
