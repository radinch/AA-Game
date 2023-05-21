package model;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class Map {
    private final Circle mainCircle;
    private final HashMap<Ball,Line> circles;
    private final ArrayList<Ball> balls = new ArrayList<>();
    private final double centerX;
    private final double centerY;
    private Text text;
    Map(Circle mainCircle, HashMap<Ball,Line> circles,double centerX,double centerY,Text text) {
        this.mainCircle = mainCircle;
        this.circles = circles;
        this.centerX = centerX;
        this.centerY = centerY;
        this.text = text;
    }

    public Circle getMainCircle() {
        return mainCircle;
    }

    public HashMap<Ball, Line> getCircles() {
        return circles;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public void setBalls(int number) {
        for (int i = 0; i < number; i++) {
            balls.add(new Ball(200,500 + i*20,10));
        }
    }

    public Text getText() {
        return text;
    }

    public void setText(Integer number) {
        text.setText(number.toString());
    }
}
