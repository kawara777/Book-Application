package com.ookawara.book.application.entity;

import java.util.Objects;

public class Book {
    private int book_id;
    private String name;
    private String release_date;
    private Boolean is_purchased;
    private int category_id;
    private String category;

    public Book(int book_id, String name, String release_date, Boolean is_purchased, int category_id, String category) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return book_id == book.book_id && category_id == book.category_id && Objects.equals(name, book.name) && Objects.equals(release_date, book.release_date) && Objects.equals(is_purchased, book.is_purchased) && Objects.equals(category, book.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book_id, name, release_date, is_purchased, category_id, category);
    }
}
