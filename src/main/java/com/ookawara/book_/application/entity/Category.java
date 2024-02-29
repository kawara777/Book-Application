package com.ookawara.book_.application.entity;

public class Category {
    private int categories_id;
    private String category;

    public Category(int categories_id, String category) {
        this.categories_id = categories_id;
        this.category = category;
    }

    public int getCategories_id() {
        return categories_id;
    }

    public String getCategory() {
        return category;
    }
}
