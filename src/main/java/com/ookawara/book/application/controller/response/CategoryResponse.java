package com.ookawara.book.application.controller.response;

public class CategoryResponse {
    private String message;

    public CategoryResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
