package view;

import controller.GameController;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

public class Game extends Application { //todo add text to main circle
    private final GameController gameController = new GameController();
    @Override
    public void start(Stage stage) throws Exception {
        Pane gamePane = FXMLLoader.load(Objects.requireNonNull(Game.class.getResource("/fxml/game.fxml")));
        gameController.prepareMap(gamePane);
        Scene scene = new Scene(gamePane);
        stage.setScene(scene);
        ProgressBar progressBar = new ProgressBar();
        createProgressBar(progressBar);
        gamePane.getChildren().add(progressBar);
        Timeline rotationTimeLine = GameController.getRotationTimeline();
        rotationTimeLine.play();
        scene.addEventHandler(KeyEvent.KEY_PRESSED,gameController.getEventHandler(gamePane,scene,progressBar));
        stage.show();
    }

    private void createProgressBar(ProgressBar progressBar) {
        progressBar.setProgress(0);
        progressBar.setId("progress-bar");
    }


}
