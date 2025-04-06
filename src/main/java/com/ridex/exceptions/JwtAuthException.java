package com.ridex.exceptions;


public class JwtAuthException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public JwtAuthException(String message) {
        super(message);
    }

    public JwtAuthException(String message, Throwable cause) {
        super(message, cause);
    }
}

