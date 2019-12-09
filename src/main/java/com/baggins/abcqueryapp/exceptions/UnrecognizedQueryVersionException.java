package com.baggins.abcqueryapp.exceptions;

public class UnrecognizedQueryVersionException extends RuntimeException {
    public UnrecognizedQueryVersionException(String s) {
        super(s);
    }
}
