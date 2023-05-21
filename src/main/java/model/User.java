package model;

import java.util.ArrayList;

public class User {

    private String username;
    private String password;
    private Avatar avatar;
    public User(String username, String password,Avatar avatar) {
        this.username = username;
        this.password = password;
        this.avatar = avatar;
    }
    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
}
