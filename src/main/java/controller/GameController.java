package controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.Ball;
import model.DataBank;
import view.Game;
import view.animaitons.BallTransition;
import view.animaitons.BallsMovement;

import java.util.Map;


public class GameController {

    private static int remainedBalls = DataBank.getNumberOfBalls();
    private static boolean isFreeze = false;
    private static int angleForRotation = 360;
    private static int ballsForFreeze = 0;
    private static boolean hasPhaseTwoStarted = false;
    private static boolean hasPhaseThreeStarted = false;
    private static boolean hasPhaseFourStarted = false;
    private static Timeline sizeTimeLine;
    private static Timeline visibilityTimeLine;
    private static int counterForReverse = 0;
    private static int angleOfBall = 0;
    private static int deltaAngle = 1;
    private static Timeline angleTimeLine;
    public static int score = 0;
    public static Text scoreOfPlayer;
    private static int timeSeconds = 0;
    private static Timeline timerTimeLine;
    private static boolean isGamePaused = false;
    private static Media media = new Media(GameController.class.getResource("/MEDIA/music1.mp3").toString());
    private static MediaPlayer mediaPlayer = new MediaPlayer(media);

    public void prepareMap(Pane pane) {
        pane.getChildren().add(DataBank.getCurrentMap().getMainCircle());
        for (java.util.Map.Entry<Ball, Line> entry : DataBank.getCurrentMap().getCircles().entrySet()) {
            pane.getChildren().addAll(entry.getKey(), entry.getValue());
        }
        for (int i = 0; i < 9; i++) {
            pane.getChildren().add(DataBank.getCurrentMap().getBalls().get(i));
        }
        //pane.getChildren().add(DataBank.getCurrentMap().getText());
        DataBank.getCurrentMap().setText(DataBank.getNumberOfBalls());
    }

    public EventHandler<KeyEvent> getEventHandler(Pane pane, ProgressBar progressBar, Text angle, Text score, Text timer) {
        scoreOfPlayer = score;
        startTimer(timer);
        playTrackOfGame();
        pauseTimeLines();
        Timeline musicTimeLine = new Timeline(new KeyFrame(Duration.millis(250),actionEvent -> resumeTimeLines()));
        musicTimeLine.setCycleCount(1);
        musicTimeLine.play();
        EventHandler<KeyEvent> event = keyEvent -> {
            String keyName = keyEvent.getCode().getName();
            if (keyName.equals("Tab") && ballsForFreeze == 5 && !isGamePaused) {
                ballsForFreeze = 0;
                progressBar.setProgress(0);
                isFreeze = true;
                getRotationTimeline().play();
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(DataBank.getFreezeTimer()),
                        actionEvent -> returnToNormal()));
                timeline.setCycleCount(1);
                timeline.play();
            } else if (keyName.equals("Space") && !isGamePaused) {
                // playShootingSound();
                new BallTransition(DataBank.getCurrentMap().getBalls().get(0), pane, Math.toRadians(angleOfBall)).play();
                remainedBalls -= 1;
                DataBank.getCurrentMap().setText(remainedBalls);
                ballsForFreeze += 1;
                ballsForFreeze = Math.min(5, ballsForFreeze);
                progressBar.setProgress(ballsForFreeze * 1.0 / 5);
                DataBank.getCurrentMap().getBalls().remove(DataBank.getCurrentMap().getBalls().get(0));
                if (DataBank.getCurrentMap().getBalls().size() >= 9)
                    pane.getChildren().add(DataBank.getCurrentMap().getBalls().get(8));
                if (remainedBalls != 0)
                    new BallsMovement(DataBank.getCurrentMap().getBalls()).play();
                if (remainedBalls <= DataBank.getNumberOfBalls() * 0.75 && !hasPhaseTwoStarted) {
                    changeToPhaseTwo(pane);
                    hasPhaseTwoStarted = true;
                }
                if (remainedBalls <= DataBank.getNumberOfBalls() * 0.5 && !hasPhaseThreeStarted) {
                    changeToPhaseThree();
                    hasPhaseThreeStarted = true;
                }
                if (remainedBalls <= DataBank.getNumberOfBalls() * 0.25 && !hasPhaseFourStarted) {
                    changeToPhaseFour(angle);
                    hasPhaseFourStarted = true;
                }
            } else if (keyName.equals("Right") && hasPhaseFourStarted && !isGamePaused) {
                moveRight();
            } else if (keyName.equals("Left") && hasPhaseFourStarted && !isGamePaused) {
                moveLeft();
            } else if (keyName.equals("Esc")) {
                isGamePaused = true;
                pauseGame(pane);
            }
        };
        return event;
    }

    private void playShootingSound() {
        Media media = new Media(BallTransition.class.getResource("/MEDIA/shooting.mp3").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
    }

    private void playTrackOfGame() {
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        mediaPlayer.play();
    }


    private void startTimer(Text timer) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timeSeconds += 1;
            updateTimer(timer);
        }));
        timeline.setCycleCount(-1);
        timeline.play();
        GameController.timerTimeLine = timeline;
    }

    private void updateTimer(Text timer) {
        int minutes = timeSeconds / 60;
        int seconds = timeSeconds % 60;
        timer.setText(String.format("%02d:%02d", minutes, seconds));
    }


    public void moveLeft() {
        if (DataBank.getCurrentMap().getBalls().get(0).getCenterX() > 15)
            DataBank.getCurrentMap().getBalls().get(0).setCenterX(DataBank.getCurrentMap().getBalls().get(0).getCenterX() - 15);
    }

    public void moveRight() {
        if (DataBank.getCurrentMap().getBalls().get(0).getCenterX() < 385)
            DataBank.getCurrentMap().getBalls().get(0).setCenterX(DataBank.getCurrentMap().getBalls().get(0).getCenterX() + 15);
    }

    private void changeToPhaseTwo(Pane pane) {
        Timeline sizeTimeLine = new Timeline(new KeyFrame(Duration.seconds(1),
                actionEvent -> changeSizeOfBalls(pane)));
        sizeTimeLine.setCycleCount(-1);
        sizeTimeLine.play();
        GameController.sizeTimeLine = sizeTimeLine;
    }

    private void changeToPhaseThree() {
        Timeline visibilityTimeLine = new Timeline(new KeyFrame(Duration.seconds(1),
                actionEvent -> changeVisibility()));
        visibilityTimeLine.setCycleCount(-1);
        visibilityTimeLine.play();
        GameController.visibilityTimeLine = visibilityTimeLine;
    }

    private void changeToPhaseFour(Text angle) {
        Timeline angleTimeline = new Timeline(new KeyFrame(Duration.millis(500 / 1.5 * DataBank.getWindSpeed()),
                actionEvent -> changeAngle(angle)));
        angleTimeline.setCycleCount(-1);
        GameController.angleTimeLine = angleTimeline;
        angleTimeline.play();
    }

    private void changeAngle(Text angle) {
        angleOfBall += deltaAngle;
        if (angleOfBall >= 15 && deltaAngle > 0) {
            deltaAngle *= -1;
        }
        if (angleOfBall <= -15 && deltaAngle < 0) {
            deltaAngle *= -1;
        }
        angle.setText("angle: " + angleOfBall);
    }

    public static int getRemainedBalls() {
        return remainedBalls;
    }

    private void changeVisibility() {
        for (Map.Entry<Ball, Line> entry : DataBank.getCurrentMap().getCircles().entrySet()) {
            if (entry.getKey().isVisible()) {
                entry.getKey().setVisible(false);
                entry.getValue().setVisible(false);
            } else {
                entry.getValue().setVisible(true);
                entry.getKey().setVisible(true);
            }
        }
    }

    private void changeSizeOfBalls(Pane pane) {
        counterForReverse++;
        for (Map.Entry<Ball, Line> ballLineEntry : DataBank.getCurrentMap().getCircles().entrySet()) {
            if (ballLineEntry.getKey().getRadius() >= 7.9 && ballLineEntry.getKey().getRadius() <= 8.1)
                ballLineEntry.getKey().setRadius(ballLineEntry.getKey().getRadius() * 1.15);
            else
                ballLineEntry.getKey().setRadius(8);
        }
        if (isCollisionHappened()) {
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1),
                    actionEvent -> afterCollision(pane)));
            timeline.setCycleCount(1);
            timeline.play();
        }
        if (counterForReverse >= 4) {
            int random_int = (int) Math.floor(Math.random() * 2 + 1);
            if (random_int == 2) {
                counterForReverse = 0;
                angleForRotation *= -1;
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1),
                        actionEvent -> getRotationTimeline().play()));
                timeline.setCycleCount(1);
                timeline.play();
            }
        }
    }


    public static Timeline getRotationTimeline() {
        double duration = 20 * 1.0 / DataBank.getRotationSpeed();
        if (isFreeze)
            duration *= 3;
        Rotate rotation = new Rotate();
        rotation.setPivotX(DataBank.getCurrentMap().getCenterX());
        rotation.setPivotY(DataBank.getCurrentMap().getCenterY());
        DataBank.getCurrentMap().getMainCircle().getTransforms().add(rotation);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(rotation.angleProperty(), 0)),
                new KeyFrame(Duration.seconds(duration),
                        new KeyValue(rotation.angleProperty(), angleForRotation)));
        timeline.setCycleCount(-1);
        for (java.util.Map.Entry<Ball, Line> circleLineEntry : DataBank.getCurrentMap().getCircles().entrySet()) {
            if (circleLineEntry.getKey().getTimeline() != null)
                circleLineEntry.getKey().getTimeline().stop();
            circleLineEntry.getKey().getTransforms().add(rotation);
            circleLineEntry.getValue().getTransforms().add(rotation);
            circleLineEntry.getKey().setTimeline(timeline);
            circleLineEntry.getKey().setRotation(rotation);
        }
        return timeline;
    }

    private void returnToNormal() {
        isFreeze = false;
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1),
                actionEvent -> getRotationTimeline().play()));
        timeline.setCycleCount(1);
        timeline.play();
    }

    private boolean isCollisionHappened() {
        for (Map.Entry<Ball, Line> entry1 : DataBank.getCurrentMap().getCircles().entrySet()) {
            for (Map.Entry<Ball, Line> entry2 : DataBank.getCurrentMap().getCircles().entrySet()) {
                if (entry1.getKey().getBoundsInParent().intersects(entry2.getKey().getBoundsInParent())
                        && !entry1.getKey().equals(entry2.getKey())) {//todo fix bugs of intersection
                   /* System.out.println(entry1.getKey().getCenterX() + " " + entry1.getKey().getCenterY());
                    System.out.println(entry2.getKey().getCenterX() + " " + entry2.getKey().getCenterY());
                    entry1.getKey().setFill(new Color(0,1,0,1));
                    entry2.getKey().setFill(new Color(0,0,1,1));*/
                    return true;
                }
            }
        }
        return false;
    }

    private static void pauseTimeLines() {
        if (sizeTimeLine != null)
            sizeTimeLine.pause();
        for (Map.Entry<Ball, Line> entry : DataBank.getCurrentMap().getCircles().entrySet()) {
            entry.getKey().getTimeline().pause();
            if (!entry.getKey().isVisible()) {
                entry.getKey().setVisible(true);
                entry.getValue().setVisible(true);
            }
        }
        if (visibilityTimeLine != null)
            visibilityTimeLine.pause();
        if (angleTimeLine != null) {
            angleTimeLine.pause();
        }
        if (timerTimeLine != null)
            timerTimeLine.pause();

    }

    public static void afterCollision(Pane pane) {
        isGamePaused = true;
        BackgroundFill backgroundFill = new BackgroundFill(Color.color(1, 0, 0), CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        pane.setBackground(background);
        pauseTimeLines();
        changeScores();
    }


    public static void afterWin(Pane pane) {
        isGamePaused = true;
        BackgroundFill backgroundFill = new BackgroundFill(Color.color(0.12, 0.55, 0.184), CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        pane.setBackground(background);
        pauseTimeLines();
        changeScores();
    }

    private static void changeScores() {
        if (DataBank.getCurrentUser() != null) {
            if (DataBank.getCurrentUser().getHighScore() < score * DataBank.getDifficultyDegree())
                DataBank.getCurrentUser().setHighScore(score * DataBank.getDifficultyDegree());
        }
    }

    private void pauseGame(Pane pane) {
        preparePauseMenu(pane);
        pauseTimeLines();
    }

    private void preparePauseMenu(Pane pane) {
        Rectangle rectangle = new Rectangle(0, 0, 400, 625);
        rectangle.setFill(Color.web("#F0E68C"));
        pane.getChildren().add(rectangle);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setLayoutX(75);
        vBox.setLayoutY(150);
        vBox.setSpacing(20);

        Button help = new Button();
        help.setId("setting-button");
        help.setText("help");

        Button music = new Button();
        music.setId("setting-button");
        music.setText("music");
        music.setOnMousePressed(mouseEvent -> changeMusic(pane));

        Button mute = new Button();
        mute.setId("setting-button");
        mute.setText("mute");
        mute.setOnMousePressed(mouseEvent -> muteMusic(pane));

        Button restart = new Button();
        restart.setId("setting-button");
        restart.setText("restart");
        restart.setOnMousePressed(mouseEvent -> {
            try {
                restartGame();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Button resume = new Button();
        resume.setId("setting-button");
        resume.setText("resume");

        Button exit = new Button();
        exit.setId("setting-button");
        exit.setText("exit");
        exit.setOnMousePressed(mouseEvent -> exitGame());

        vBox.getChildren().addAll(help, music, mute, restart, resume, exit);
        resume.setOnMousePressed(mouseEvent -> resumeGame(pane, vBox, rectangle));
        help.setOnMousePressed(mouseEvent -> showHelp(pane,vBox));
        pane.getChildren().add(vBox);
    }

    private void exitGame() {
        DataBank.getStage().close();
    }

    private void resumeGame(Pane pane, VBox vBox, Rectangle rectangle) {
        isGamePaused = false;
        pane.getChildren().removeAll(vBox, rectangle);
        resumeTimeLines();
    }

    private void resumeTimeLines() {
        if (sizeTimeLine != null)
            sizeTimeLine.play();
        for (Map.Entry<Ball, Line> entry : DataBank.getCurrentMap().getCircles().entrySet()) {
            entry.getKey().getTimeline().play();
        }
        if (visibilityTimeLine != null)
            visibilityTimeLine.play();
        if (angleTimeLine != null) {
            angleTimeLine.play();
        }
        if (timerTimeLine != null)
            timerTimeLine.play();
    }

    private void restartGame() throws Exception {
        DataBank.setCurrentMap(DataBank.getMapNumber());
        restartFields();
        MainMenuController.prepareGame();
        new Game().start(DataBank.getStage());
    }

    private void restartFields() {
        remainedBalls = DataBank.getNumberOfBalls();
        isFreeze = false;
        angleForRotation = 360;
        ballsForFreeze = 0;
        hasPhaseTwoStarted = false;
        hasPhaseThreeStarted = false;
        hasPhaseFourStarted = false;
        sizeTimeLine = null;
        visibilityTimeLine = null;
        counterForReverse = 0;
        angleOfBall = 0;
        deltaAngle = 1;
        angleTimeLine = null;
        score = 0;
        scoreOfPlayer = null;
        timeSeconds = 0;
        timerTimeLine = null;
        isGamePaused = false;
        media = new Media(getClass().getResource("/MEDIA/music1.mp3").toString());
        mediaPlayer = new MediaPlayer(media);
    }

    private void muteMusic(Pane pane) {
        mediaPlayer.stop();
    }

    private void showHelp(Pane pane,VBox vBox) {
        VBox vBox1 = new VBox();
        vBox1.setSpacing(20);
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setLayoutX(75);
        vBox1.setLayoutY(150);
        pane.getChildren().remove(vBox);
        Text text = new Text();
        text.setId("text3");
        text.setText("""
                SPACE: SHOOTING BALLS
                
                TAB: FREEZING MAIN CIRCLE
                
                Esc: PAUSE
                
                (FOR PHASE 4)
                
                RIGHT: MOVE BALL TO RIGHT
                
                LEFT: MOVE BALL TO LEFT""");
        Button button = new Button();
        button.setId("setting-button");
        button.setText("back");
        vBox1.getChildren().addAll(text,button);
        pane.getChildren().add(vBox1);
        button.setOnMousePressed(mouseEvent -> backToPauseMenu(pane,vBox,vBox1));
    }

    private void backToPauseMenu(Pane pane,VBox vBox, VBox vBox1) {
        pane.getChildren().remove(vBox1);
        pane.getChildren().add(vBox);
    }

    private void changeMusic(Pane pane) {
    }

}
