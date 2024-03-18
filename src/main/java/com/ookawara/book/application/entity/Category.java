package com.ookawara.book.application.entity;

import java.util.Objects;

public class Category {
    private int categoryId;
    private String category;

    public Category(int categoryId, String category) {
        this.categoryId = categoryId;
        this.category = category;
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
        Category category1 = (Category) o;
        return categoryId == category1.categoryId && Objects.equals(category, category1.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, category);
    }
}
