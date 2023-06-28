package com.seener.usedarts.model;

public class LoginInfos {
    private boolean success;
    private String message;

    public LoginInfos(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
