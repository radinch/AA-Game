package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Objects;

public class Settings extends Application {
    private final SettingsController settingsController = new SettingsController();
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = FXMLLoader.load(Objects.requireNonNull(Settings.class.getResource("/fxml/settings.fxml")));
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(20);
        ChoiceBox<Integer> difficultyBox = new ChoiceBox<>();
        ChoiceBox<Integer> mapBox = new ChoiceBox<>();
        ChoiceBox<Integer> numberOfBallsBox = new ChoiceBox<>();
        settingsController.prepareDifficultyBox(difficultyBox,vBox);
        settingsController.prepareMapBox(mapBox,vBox);
        settingsController.prepareNumberOfBallsBox(numberOfBallsBox,vBox);
        Button submit = new Button();
        submit.setId("setting-button");
        submit.setText("Submit");
        submit.setOnAction(actionEvent -> {
            try {
                settingsController.submit(difficultyBox,mapBox,numberOfBallsBox);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        vBox.getChildren().add(submit);
        borderPane.setCenter(vBox);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }

}
