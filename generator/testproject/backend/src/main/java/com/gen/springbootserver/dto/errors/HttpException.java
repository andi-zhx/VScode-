package com.gen.springbootserver.dto.errors;


import org.springframework.http.HttpStatus;

public abstract class HttpException extends RuntimeException {

    private static final long serialVersionUID = 7663297799945747424L;
    private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    protected HttpException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    protected HttpException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    protected HttpException(ApiException exception) {
        super(exception);
    }

    protected HttpException(ApiException exception, HttpStatus httpStatus) {
        this(exception);
        this.httpStatus = httpStatus;
    }

    HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

}
