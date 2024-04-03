package com.ookawara.book.application.exception;

public class BookConflictException extends RuntimeException {
    public BookConflictException(String message) {
        super(message);
    }
}
