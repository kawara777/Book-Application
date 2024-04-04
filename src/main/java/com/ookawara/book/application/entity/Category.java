package com.ookawara.book.application.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

public class Category {
    private Integer categoryId;
    private String category;

    public Category(Integer categoryId, String category) {
        this.categoryId = categoryId;
        this.category = category;
    }

    public Category(String category) {
        this.category = category;
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
        Category category1 = (Category) o;
        return categoryId == category1.categoryId && Objects.equals(category, category1.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, category);
    }
}
