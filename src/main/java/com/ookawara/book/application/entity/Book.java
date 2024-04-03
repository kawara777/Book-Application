package com.ookawara.book.application.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Book {
    private Integer bookId;
    private String name;
    private LocalDate releaseDate;
    private Boolean isPurchased;
    private int categoryId;
    private String category;

    public Book() {}

    public Book(Integer bookId, String name, LocalDate releaseDate, Boolean isPurchased, int categoryId, String category) {
        this.bookId = bookId;
        this.name = name;
        this.releaseDate = releaseDate;
        this.isPurchased = isPurchased;
        this.categoryId = categoryId;
        this.category = category;
    }

    public Book(String name, LocalDate releaseDate, Boolean isPurchased, int categoryId) {
        // bookId は INSERT ⽂発⾏時に MySQL によって⾃動採番した値が補完されるので null を設定
        this.bookId = null;
        this.name = name;
        this.releaseDate = releaseDate;
        this.isPurchased = isPurchased;
        this.categoryId = categoryId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public String getName() {
        return name;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Boolean getIsPurchased() {
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
