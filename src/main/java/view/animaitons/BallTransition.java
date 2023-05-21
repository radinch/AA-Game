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
    private final double angle;
    public BallTransition(Ball ball, Pane pane,double angle) {
        this.ball = ball;
        this.pane = pane;
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
        this.angle = angle;
    }

    @Override
    protected void interpolate(double v) {
        double y = Math.max(ball.getCenterY() - 20, 375);
        if (checkTerritory(y)) {
            Ball helpBall = null;
            for (Map.Entry<Ball, Line> entry : DataBank.getCurrentMap().getCircles().entrySet()) {
                helpBall = entry.getKey();
                break;
            }
            Line line = new Line(200, 225, 200, 375);
            line.setStrokeWidth(1.5);
            pane.getChildren().add(line);
            assert helpBall != null;
            if (helpBall.isVisible()) {
                line.setVisible(true);
            } else {
                line.setVisible(false);
                ball.setVisible(false);
            }
            DataBank.getCurrentMap().getCircles().put(ball, line);
            GameController.getRotationTimeline().play();
            ball.setRadius(helpBall.getRadius());
            if (isCollisionHappened()) {
                GameController.afterCollision(pane);
            }
            this.stop();
        }
        if ( ball.getCenterY() <= 0 || ball.getCenterX() <= 0 || ball.getCenterX() >= 400)
            GameController.afterCollision(pane);
        ball.setCenterY(y);
    }


    private boolean isCollisionHappened() {
        for (Map.Entry<Ball, Line> ballLineEntry : DataBank.getCurrentMap().getCircles().entrySet()) {
            if (ballLineEntry.getKey().getBoundsInParent().intersects(ball.getBoundsInParent()) &&
                    !ballLineEntry.getKey().equals(ball))
                return true;
        }
        return false;
    }

    private boolean checkTerritory(double y) {
        double mainCircleX = DataBank.getCurrentMap().getMainCircle().getCenterX();
        double mainCircleY = DataBank.getCurrentMap().getMainCircle().getCenterY();
        double distanceX = Math.pow(mainCircleX - ball.getCenterX(), 2);
        double distanceY = Math.pow(mainCircleY - y, 2);
        double distance = Math.sqrt(distanceY + distanceX);
        System.out.println(distance);
        return distance <= 150;
    }

    private int checkPathOfBall() {

    }
}
