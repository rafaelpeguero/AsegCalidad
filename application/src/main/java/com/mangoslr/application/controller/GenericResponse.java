package com.mangoslr.application.controller;

import java.util.Locale;

public class GenericResponse {
    private String message;
    private String error;

    public GenericResponse(String message) {
        super();
        this.message = message;
    }


    public GenericResponse(String message, String error) {
        super();
        this.message = message;
        this.error = error;
    }

    public String getMessage(String s, Object o, Locale locale) {
        return message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "GenericResponse{" +
                "message='" + message + '\'' +
                ", error='" + error + '\'' +
                '}';
    }

    //---
//        return "";
//    }
}