package com.ridex.exceptions;

public class FirebaseAuthExceptions extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FirebaseAuthExceptions(String message) {
        super(message);
    }

    public FirebaseAuthExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}
