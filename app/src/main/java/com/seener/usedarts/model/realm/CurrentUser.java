package com.seener.usedarts.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class CurrentUser extends RealmObject {

    @PrimaryKey
    private int id;

    @Required
    private String email;
    private String displayName;
    private String token;

    public CurrentUser() {
    }

    public CurrentUser(String email, String displayName, String token) {
        this.email = email;
        this.displayName = displayName;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
