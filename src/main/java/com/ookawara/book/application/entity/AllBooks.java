package com.ookawara.book.application.entity;

public class AllBooks {
    private int book_id;
    private String name;
    private String release_date;
    private Boolean is_purchased;
    private int category_id;
    private String category;

    public AllBooks(int book_id, String name, String release_date, Boolean is_purchased, int category_id, String category) {
        this.book_id = book_id;
        this.name = name;
        this.release_date = release_date;
        this.is_purchased = is_purchased;
        this.category_id = category_id;
        this.category = category;
    }

    public int getBookId() {
        return book_id;
    }

    public String getName() {
        return name;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public boolean isIsPurchased() {
        return is_purchased;
    }

    public int getCategoryId() {
        return category_id;
    }

    public String getCategory() {
        return category;
    }
}
