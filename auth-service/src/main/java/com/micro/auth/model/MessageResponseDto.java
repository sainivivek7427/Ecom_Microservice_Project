package com.micro.auth.model;

import org.springframework.http.HttpStatus;

public class MessageResponseDto {
    private boolean success;
    private HttpStatus status;

    public MessageResponseDto() {
    }
    public MessageResponseDto(boolean success, HttpStatus status) {
        this.success = success;

        this.status = status;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public HttpStatus getStatus() {
        return status;
    }
    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
