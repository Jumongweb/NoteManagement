package com.africa.semicolon.exceptions;

public class UserNotFoundException extends NoteManagementException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
