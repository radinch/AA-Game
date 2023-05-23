package view.animaitons;

import controller.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import model.Ball;
import model.DataBank;
import view.Game;

import java.util.Map;

public class BallTransition extends Transition {
    private final Ball ball;
    private final Pane pane;
    private final double angle;
    private final double firstX;

    public BallTransition(Ball ball, Pane pane, double angle) {
        this.ball = ball;
        this.pane = pane;
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
        this.angle = angle;
        firstX = ball.getCenterX() - 200;
    }

    @Override
    protected void interpolate(double v) {
        double x;
        double y = Math.max(ball.getCenterY() - 20, 500 - getPathDistanceForY());
        if (angle > 0) {
            x = Math.min(ball.getCenterX() + 20 * Math.tan(angle), 200 + firstX +  getPathDistanceForY() * Math.tan(angle));
        } else {
            x = Math.max(ball.getCenterX() + 20 * Math.tan(angle), 200 + firstX + getPathDistanceForY() * Math.tan(angle));
        }
        if(getPathDistanceForY() == -1) {
            y=ball.getCenterY() -20;
            x= ball.getCenterX() + 20 * Math.tan(angle);
        }
        if (checkTerritory(y, x)) {
            Ball helpBall = null;
            for (Map.Entry<Ball, Line> entry : DataBank.getCurrentMap().getCircles().entrySet()) {
                helpBall = entry.getKey();
                break;
            }
            Line line = new Line(200, 225, x, y);
            line.setStrokeWidth(1.5);
            pane.getChildren().add(line);
            assert helpBall != null;
            if (helpBall.isVisible()) {
                line.setVisible(true);
            } else {
                line.setVisible(false);
                ball.setVisible(false);
            }
            ball.setCenterY(y);
            ball.setCenterX(x);
            DataBank.getCurrentMap().getCircles().put(ball, line);
            GameController.getRotationTimeline().play();
            ball.setRadius(helpBall.getRadius());
            if (isCollisionHappened()) {
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1),
                        actionEvent -> GameController.afterCollision(pane)));
                timeline.setCycleCount(1);
                timeline.play();
            }
            else  {
                GameController.score += 1;
                GameController.scoreOfPlayer.setText("score: " + GameController.score);
                if(GameController.getRemainedBalls() == 0)
                    GameController.afterWin(pane);
            }
            this.stop();
        }
        if (ball.getCenterY() <= 0 || ball.getCenterX() <= 0 || ball.getCenterX() >= 400)
            GameController.afterCollision(pane);
        ball.setCenterX(x);
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

    private boolean checkTerritory(double y, double x) {
        double mainCircleX = DataBank.getCurrentMap().getMainCircle().getCenterX();
        double mainCircleY = DataBank.getCurrentMap().getMainCircle().getCenterY();
        double distanceX = Math.pow(mainCircleX - x, 2);
        double distanceY = Math.pow(mainCircleY - y, 2);
        double distance = Math.sqrt(distanceY + distanceX);
        return distance <= 150 + 0.1 && distance >= 150 - 0.1;
    }

    /*private double getPathDistanceForY() {
        double delta = 22 * 22 - 340 / (Math.pow(Math.cos(angle), 2));
        if (delta < 0)
            return -1;
        return 25 * (22 - Math.sqrt(delta)) * Math.pow(Math.cos(angle),2) / 2;
    }*/

    private double getPathDistanceForY() {
        double firstX = this.firstX;
        double delta = Math.pow(550 - 2*firstX*Math.tan(angle),2) - 4*(53125 + firstX*firstX)/Math.pow(Math.cos(angle),2);
        if(delta < 0)
            return -1;
        return (550 - 2*firstX*Math.tan(angle) -Math.sqrt(delta)) * Math.pow(Math.cos(angle),2) / 2;
    }
}
