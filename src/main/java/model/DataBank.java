package model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DataBank {
    private static ArrayList<User> users = new ArrayList<>();
    private static Map currentMap = getMapOne();
    private static User currentUser;
    private static Stage stage;
    private static int difficultyDegree = 1;
    private static int rotationSpeed;
    private static double windSpeed;
    private static int freezeTimer;
    private static int numberOfBalls = 20;

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        DataBank.users = users;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        DataBank.currentUser = currentUser;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        DataBank.stage = stage;
    }

    public static int getDifficultyDegree() {
        return difficultyDegree;
    }

    public static void setDifficultyDegree(int difficultyDegree) {
        DataBank.difficultyDegree = difficultyDegree;
    }

    public static int getRotationSpeed() {
        return rotationSpeed;
    }

    public static void setRotationSpeed(int rotationSpeed) {
        DataBank.rotationSpeed = rotationSpeed;
    }

    public static double getWindSpeed() {
        return windSpeed;
    }

    public static void setWindSpeed(double windSpeed) {
        DataBank.windSpeed = windSpeed;
    }

    public static int getFreezeTimer() {
        return freezeTimer;
    }

    public static void setFreezeTimer(int freezeTimer) {
        DataBank.freezeTimer = freezeTimer;
    }

    private static Map getMapOne() {
        return new Map(getMainCircle(), getMapOneCircles(), 200, 225,getMainCircleText());
    }

    private static Map getMapTwo() {
        return null;
    }

    private
    static Map getMapThree() {
        return null;
    }

    public static Map getCurrentMap() {
        return currentMap;
    }

    public static void setCurrentMap(int mapNumber) {
        switch (mapNumber) {
            case 1 -> currentMap = getMapOne();
            case 2 -> currentMap = getMapTwo();
            case 3 -> currentMap = getMapThree();
        }
    }

    public static int getNumberOfBalls() {
        return numberOfBalls;
    }

    public static void setNumberOfBalls(int numberOfBalls) {
        DataBank.numberOfBalls = numberOfBalls;
    }

    public static void initializeGameDegrees() {
        switch (difficultyDegree) {
            case 1 -> {
                rotationSpeed = 5;
                windSpeed = 1.2;
                freezeTimer = 7;
            }
            case 2 -> {
                rotationSpeed = 10;
                windSpeed = 1.5;
                freezeTimer = 5;
            }
            case 3 -> {
                rotationSpeed = 15;
                windSpeed = 1.8;
                freezeTimer = 3;
            }
        }

    }

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public static ArrayList<User> readFromJson() {
        File file = new File("users.json");
        ObjectMapper objectMapper = new ObjectMapper();
        if (file.length() != 0) {
            try {
                return objectMapper.readValue(file, new TypeReference<ArrayList<User>>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
                return new ArrayList<User>();
            }
        }
        return new ArrayList<>();
    }

    public static void writeToJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File("users.json"), DataBank.getUsers());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Circle getMainCircle() {
        return new Circle(200, 225, 50);
    }

    private static Text getMainCircleText() {
        Text text = new Text(180,237,"");
        text.setFill(new Color(1,1,1,1));
        text.setFont(Font.font("Tw Cen MT",40));
        return text;
    }

    private static HashMap<Ball, Line> getMapOneCircles() {
        double firstX = 200;
        double firstY = 225;
        double angle = 0;
        double length = 150;
        HashMap<Ball, Line> balls = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            double toRadiansAngle = Math.toRadians(angle);
            double finalX = firstX + length * Math.cos(toRadiansAngle);
            double finalY = firstY + length * Math.sin(toRadiansAngle);
            Line line = new Line(firstX, firstY, finalX, finalY);
            line.setStrokeWidth(1.5);
            balls.put(new Ball(finalX, finalY, 8), line);
            angle += 72;
        }
        return balls;
    }

}
