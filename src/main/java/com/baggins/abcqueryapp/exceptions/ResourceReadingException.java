package com.baggins.abcqueryapp.exceptions;

public class ResourceReadingException extends RuntimeException {
    public ResourceReadingException(String s, Throwable e) {
        super(s, e);
    }
}
