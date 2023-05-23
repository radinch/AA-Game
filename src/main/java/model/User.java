package model;

import javafx.scene.image.Image;


public class User {

    private String username;
    private String password;
    private String avatar;
    private int highScore;
    private int time;
    public User(String username, String password,String avatar) {
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        highScore = 0;
        time = 0;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
