package com.ookawara.book_.application.entity;

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
}
