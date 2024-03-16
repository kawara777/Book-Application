package com.ookawara.book.application.entity;

import java.util.Objects;

public class Category {
    private int category_id;
    private String category;

    public Category(int category_id, String category) {
        this.category_id = category_id;
        this.category = category;
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
        Category category1 = (Category) o;
        return category_id == category1.category_id && Objects.equals(category, category1.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category_id, category);
    }
}
