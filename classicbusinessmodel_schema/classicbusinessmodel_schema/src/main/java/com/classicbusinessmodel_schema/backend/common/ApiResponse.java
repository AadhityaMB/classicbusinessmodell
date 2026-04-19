package com.classicbusinessmodel_schema.backend.common;


import java.time.LocalDateTime;

public class ApiResponse<T> {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String message;
    private T data;

    public ApiResponse() {
    }

    public ApiResponse( int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

}