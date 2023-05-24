package view;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.DataBank;

public class SettingsController {
    public void prepareDifficultyBox(ChoiceBox<Integer> difficultyBox, VBox vBox) {
        Label label = new Label();
        label.setText("Settings");
        label.setId("label");
        difficultyBox.setValue(1);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);
        Text text = new Text();
        text.setId("text1");
        text.setText("Change difficulty degree:");
        difficultyBox.setMaxHeight(25);
        ObservableList<Integer> difficultyBoxItems = difficultyBox.getItems();
        difficultyBoxItems.addAll(1,2,3);
        hBox.getChildren().addAll(text,difficultyBox);
        vBox.getChildren().addAll(label,hBox);
    }

    public void prepareMapBox(ChoiceBox<Integer> mapBox, VBox vBox) {
        mapBox.setValue(1);
        mapBox.setMaxHeight(25);
        ObservableList<Integer> mapBoxItems = mapBox.getItems();
        mapBoxItems.addAll(1,2,3);
        Text text = new Text();
        text.setText("Choose map");
        text.setId("text1");
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(text,mapBox);
        vBox.getChildren().add(hBox);
    }

    public void submit(ChoiceBox<Integer> difficultyBox, ChoiceBox<Integer> mapBox,
                       ChoiceBox<Integer> numberOfBallsBox , ChoiceBox<String> keyBox, ChoiceBox<String> freezeKeyBox) throws Exception {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Change Options Successful");
        alert.setContentText("this changes will be implemented");
        alert.showAndWait();
        DataBank.setDifficultyDegree(difficultyBox.getValue());
        DataBank.setMapNumber(mapBox.getValue());
        DataBank.setCurrentMap(mapBox.getValue());
        DataBank.setNumberOfBalls(numberOfBallsBox.getValue());
        DataBank.keyForShooting = keyBox.getValue();
        DataBank.keyForFreezing = freezeKeyBox.getValue();
        new MainMenu().start(DataBank.getStage());
    }

    public void prepareNumberOfBallsBox(ChoiceBox<Integer> numberOfBallsBox, VBox vBox) {
        numberOfBallsBox.setValue(20);
        numberOfBallsBox.setMaxHeight(25);
        ObservableList<Integer> ballsBoxItems = numberOfBallsBox.getItems();
        for (int i = 20; i <= 40; i++) {
            ballsBoxItems.add(i);
        }
        Text text = new Text();
        text.setText("Choose Number of balls");
        text.setId("text1");
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(text,numberOfBallsBox);
        vBox.getChildren().add(hBox);
    }

    public void prepareShootingKeyBox(ChoiceBox<String> keyBox, VBox vBox) {
        keyBox.setValue("Space");
        keyBox.setMaxHeight(25);
        ObservableList<String> keyBoxItems = keyBox.getItems();
        keyBoxItems.addAll("Space","Enter");
        Text text = new Text();
        text.setText("Choose shooting key");
        text.setId("text1");
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(text,keyBox);
        vBox.getChildren().add(hBox);
    }

    public void prepareFreezeKeyBox(ChoiceBox<String> freezeKeyBox, VBox vBox) {
        freezeKeyBox.setValue("Tab");
        freezeKeyBox.setMaxHeight(25);
        ObservableList<String> keyBoxItems = freezeKeyBox.getItems();
        keyBoxItems.addAll("Tab","Shift");
        Text text = new Text();
        text.setText("Choose freeze key");
        text.setId("text1");
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(text,freezeKeyBox);
        vBox.getChildren().add(hBox);
    }
}
