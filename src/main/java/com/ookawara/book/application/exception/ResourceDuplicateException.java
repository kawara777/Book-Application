package com.ookawara.book.application.exception;

public class ResourceDuplicateException extends RuntimeException {
    public ResourceDuplicateException(String message) {
        super(message);
    }
}
