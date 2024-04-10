package com.africa.semicolon.exceptions;

public class InvalidPasswordException extends NoteManagementException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
