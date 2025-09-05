package com.mailong245.hexagonalarchitecture.common.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomRuntimeException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

    public CustomRuntimeException() {
        super();
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.errorCode = "";
        this.message = "";
    }

    public CustomRuntimeException(HttpStatus httpStatus) {
        super();
        this.httpStatus = httpStatus;
        this.errorCode = "";
        this.message = "";
    }

    public CustomRuntimeException(HttpStatus httpStatus, String errorCode) {
        super();
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = "";
    }

    public CustomRuntimeException(HttpStatus httpStatus, String errorCode, String message) {
        super();
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }


}
