package view.animaitons;

import controller.GameController;
import javafx.animation.Transition;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import model.Ball;
import model.DataBank;

import java.util.Map;

public class BallTransition extends Transition {
    private final Ball ball;
    private final Pane pane;
    private final Scene scene;

    public BallTransition(Ball ball, Pane pane,Scene scene) {
        this.ball = ball;
        this.pane = pane;
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
        this.scene = scene;
    }

    @Override
    protected void interpolate(double v) {
        double y = Math.max(ball.getCenterY() - 20,375);
        if(y == 375) {
            Ball helpBall = null;
            for (Map.Entry<Ball, Line> entry : DataBank.getCurrentMap().getCircles().entrySet()) {
                helpBall = entry.getKey();
                break;
            }
            Line line = new Line(200,275,200,375);
            line.setStrokeWidth(1.5);
            pane.getChildren().add(line);
            if(helpBall.isVisible()) {
                line.setVisible(true);
            }
            else{
                line.setVisible(false);
                ball.setVisible(false);
            }
            DataBank.getCurrentMap().getCircles().put(ball,line);
            GameController.getRotationTimeline().play();
            ball.setRadius(helpBall.getRadius());
            if(isCollisionHappened()) {
                GameController.afterCollision(pane);
            }
            this.stop();
        }
        ball.setCenterY(y);
    }


    private boolean isCollisionHappened() {
        for (Map.Entry<Ball, Line> ballLineEntry : DataBank.getCurrentMap().getCircles().entrySet()) {
            if(ballLineEntry.getKey().getBoundsInParent().intersects(ball.getBoundsInParent()) &&
            !ballLineEntry.getKey().equals(ball))
                return true;
        }
        return false;
    }
}
