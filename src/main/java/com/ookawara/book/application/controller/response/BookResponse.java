package com.ookawara.book.application.controller.response;

public class BookResponse {
    private String message;

    public BookResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

