package com.gen.springbootserver.dto.errors;

public abstract class ApiException extends Exception {
    private static final long serialVersionUID = -2061318863847304479L;

    public ApiException(String message) {
        super(message);
    }
}