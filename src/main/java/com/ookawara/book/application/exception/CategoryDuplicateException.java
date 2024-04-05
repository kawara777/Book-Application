package com.ookawara.book.application.exception;

public class CategoryDuplicateException extends RuntimeException {
    public CategoryDuplicateException(String message) {
        super(message);
    }
}
