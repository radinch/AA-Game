package view;

import controller.GameController;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.DataBank;

import java.util.Objects;

public class Game extends Application {
    private final GameController gameController = new GameController();
    @Override
    public void start(Stage stage) throws Exception {
        Pane gamePane = FXMLLoader.load(Objects.requireNonNull(Game.class.getResource("/fxml/game.fxml")));
        gameController.prepareMap(gamePane);
        Scene scene = new Scene(gamePane);
        stage.setScene(scene);
        HBox hBox = new HBox();
        ProgressBar progressBar = new ProgressBar();
        Text score = new Text();
        score.setText("score: 0");
        Text angle = new Text();
        angle.setText("angle: 0");
        Text timer = new Text();
        timer.setText("00:00");
        Text remainedBalls = new Text();
        remainedBalls.setText(String.valueOf(DataBank.getNumberOfBalls()));
        createMargins(hBox,angle,progressBar,score,timer,remainedBalls);
        gamePane.getChildren().add(hBox);
        Timeline rotationTimeLine = GameController.getRotationTimeline();
        rotationTimeLine.play();
        scene.addEventHandler(KeyEvent.KEY_PRESSED,gameController.getEventHandler(gamePane, progressBar,angle,score,timer,remainedBalls));
        stage.show();
    }

    private void createMargins(HBox hBox, Text angle, ProgressBar progressBar, Text score, Text timer, Text remainedBalls) {
        hBox.setSpacing(17.5);
        progressBar.setProgress(0);
        progressBar.setId("progress-bar");
        angle.setFont(Font.font("Tw Cen MT", FontWeight.NORMAL, FontPosture.ITALIC,12.5));
        score.setFont(Font.font("Tw Cen MT", FontWeight.NORMAL, FontPosture.ITALIC,12.5));
        timer.setFont(Font.font("Tw Cen MT", FontWeight.NORMAL, FontPosture.ITALIC,12.5));
        remainedBalls.setFont(Font.font("Tw Cen MT", FontWeight.NORMAL, FontPosture.ITALIC,12.5));
        hBox.getChildren().addAll(progressBar,angle,score,timer,remainedBalls);
    }



}
