package com.baggins.abcqueryapp.exceptions;

import java.sql.SQLException;

public class EmployeeNotFoundException extends SQLException {
    public EmployeeNotFoundException(Long id) {
        super(String.format("Failed to find user with ID: '%d'", id));
    }
}
