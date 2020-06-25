package com.gen.springbootserver.dto.errors;

import org.springframework.http.HttpStatus;

public class CommonHttpException extends HttpException {

    private static final long serialVersionUID = -3892405033709420396L;

    public CommonHttpException(String message, HttpStatus status) {
        super(message, status);
    }

    public CommonHttpException(HttpStatus status) {
        super(status);
    }
    
}