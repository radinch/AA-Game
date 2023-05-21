package model;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;

public class Ball extends Circle {
    private Timeline timeline;

    private Rotate rotation;


    public Ball(double v, double v1, double v2) {
        super(v, v1, v2);
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public void setRotation(Rotate rotation) {
        this.rotation = rotation;
    }

    public Rotate getRotation() {
        return rotation;
    }
}
