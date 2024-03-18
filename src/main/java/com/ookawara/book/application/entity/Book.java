package com.ookawara.book.application.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Book {
    private int bookId;
    private String name;
    private LocalDate releaseDate;
    private Boolean isPurchased;
    private int categoryId;
    private String category;

    public Book(int bookId, String name, LocalDate releaseDate, Boolean isPurchased, int categoryId, String category) {
        this.bookId = bookId;
        this.name = name;
        this.releaseDate = releaseDate;
        this.isPurchased = isPurchased;
        this.categoryId = categoryId;
        this.category = category;
    }

    public int getBookId() {
        return bookId;
    }

    public String getName() {
        return name;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public boolean isIsPurchased() {
        return isPurchased;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return bookId == book.bookId && categoryId == book.categoryId && Objects.equals(name, book.name) && Objects.equals(releaseDate, book.releaseDate) && Objects.equals(isPurchased, book.isPurchased) && Objects.equals(category, book.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, name, releaseDate, isPurchased, categoryId, category);
    }
}
