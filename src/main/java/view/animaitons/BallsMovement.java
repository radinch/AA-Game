package view.animaitons;

import javafx.animation.Transition;
import javafx.util.Duration;
import model.Ball;

import java.util.ArrayList;

public class BallsMovement extends Transition {
    private final ArrayList<Ball> balls;

    public BallsMovement(ArrayList<Ball> balls) {
        this.balls = balls;
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        double y = balls.get(0).getCenterY() - 5;
        if(y <= 500) {
            this.stop();
        }
        for (Ball ball : balls) {
            ball.setCenterY(ball.getCenterY() - 5);
        }
    }
}
