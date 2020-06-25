package com.gen.springbootserver.dto.errors;

public class TokenValidationException extends AuthenticationException {
    private static final long serialVersionUID = 1L;

    public TokenValidationException(String message) {
        super(message);
    }

    public TokenValidationException() {
    }
}