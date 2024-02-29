package com.ookawara.book_.application.entity;

import java.time.LocalDate;

public class BookAllData {
    private int books_id;
    private String name;
    private LocalDate release_date;
    private boolean is_purchased;
    private int category_id;
    private String category;

    public BookAllData(int books_id, String name, LocalDate release_date, boolean is_purchased, int category_id, String category) {
        this.books_id = books_id;
        this.name = name;
        this.release_date = release_date;
        this.is_purchased = is_purchased;
        this.category_id = category_id;
        this.category = category;
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

    public String getCategory() {
        return category;
    }
}
