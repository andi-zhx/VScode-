package com.gen.springbootserver.dto.errors;

public class CommonApiException extends ApiException {

    private static final long serialVersionUID = 2297061559362437968L;

    public CommonApiException(String mes) {
        super(mes);
    }
}
