package com.seener.usedarts.model;

public class SaveUserInfo {
    private boolean success;
    private String message;

    public SaveUserInfo(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "SaveUserInfo{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
