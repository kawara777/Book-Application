package com.ookawara.book.application.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Book {
    private Integer bookId;
    private String name;
    private LocalDate releaseDate;
    private Boolean isPurchased;
    private Integer categoryId;
    private String category;

    //    MyBatis がインスタンス生成するのに必要
    public Book() {
    }

    public Book(Integer bookId, String name, LocalDate releaseDate, Boolean isPurchased, Integer categoryId, String category) {
        this.bookId = bookId;
        this.name = name;
        this.releaseDate = releaseDate;
        this.isPurchased = isPurchased;
        this.categoryId = categoryId;
        this.category = category;
    }

    public Book(String name, LocalDate releaseDate, Boolean isPurchased, Integer categoryId) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.isPurchased = isPurchased;
        this.categoryId = categoryId;
    }

    public Book(Integer bookId, String name, LocalDate releaseDate, Boolean isPurchased, Integer categoryId) {
        this.bookId = bookId;
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

    public Integer getCategoryId() {
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
