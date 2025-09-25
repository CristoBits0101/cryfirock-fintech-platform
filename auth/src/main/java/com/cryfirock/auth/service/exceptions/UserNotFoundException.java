package com.cryfirock.auth.service.exceptions;

// Lanza una excepción en tiempo de ejecución
public class UserNotFoundException extends RuntimeException {

    // Recibe el mensaje y lo pasa a la clase padre
    public UserNotFoundException(String message) {
        super(message);
    }

}