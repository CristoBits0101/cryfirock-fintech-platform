package com.cryfirock.auth.service.exceptions;

// Throws a runtime exception
public class UserNotFoundException extends RuntimeException {

    // Receives the message and passes it to the father
    public UserNotFoundException(String message) {
        super(message);
    }

}