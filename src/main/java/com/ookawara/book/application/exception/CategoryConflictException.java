package com.ookawara.book.application.exception;

public class CategoryConflictException extends RuntimeException {
    public CategoryConflictException(String message) {
        super(message);
    }
}
