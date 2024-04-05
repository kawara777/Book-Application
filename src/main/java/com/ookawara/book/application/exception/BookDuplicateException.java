package com.ookawara.book.application.exception;

public class BookDuplicateException extends RuntimeException {
    public BookDuplicateException(String message) {
        super(message);
    }
}
