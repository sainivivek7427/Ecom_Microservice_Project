package com.micro.auth.exception;

import org.springframework.http.HttpStatus;

public class LoginException extends RuntimeException {
    private String message;
    private HttpStatus status;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LoginException(String message, HttpStatus status) {

        super();
        this.message=message;
        this.status=status;
    }
}
