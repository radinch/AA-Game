package view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Avatar;
import model.DataBank;

import java.io.File;
import java.net.URL;

public class AvatarMenu extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/avatarMenu.fxml");
        Pane pane = FXMLLoader.load (url);
        addAvatars(pane);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private void addAvatars(Pane pane) {
        Text text = new Text();
        text.setText("Choose avatar");
        text.setId("label");
        text.setX(100);
        text.setY(100);
        pane.getChildren().add(text);
        for (int i = 1; i <= 3 ; i++) {
            int j = 7-i;
            Avatar avatar1 = new Avatar(100,100 + i*100,AvatarMenu.class.getResource("/IMAGE/" +  i +".png").toExternalForm());
            Avatar avatar2 = new Avatar(300,100 + i*100,AvatarMenu.class.getResource("/IMAGE/" +  j +".png").toExternalForm());
            pane.getChildren().addAll(avatar1,avatar2);
            int finalI = i;
            avatar1.setOnMousePressed(me -> setAvatar(
                    AvatarMenu.class.getResource("/IMAGE/" + finalI +".png").toExternalForm()));
            avatar2.setOnMousePressed(me -> setAvatar(
                    AvatarMenu.class.getResource("/IMAGE/" + j +".png").toExternalForm()));
        }
        Hyperlink hyperlink = new Hyperlink();
        hyperlink.setLayoutX(157.5);
        hyperlink.setLayoutY(462.5);
        hyperlink.setText("upload avatar?");
        hyperlink.setOnMousePressed(mouseEvent -> uploadFile());
        Button button = new Button();
        button.setId("setting-button");
        button.setText("back");
        button.setLayoutX(80);
        button.setLayoutY(500);
        button.setOnMousePressed(mouseEvent -> {
            try {
                back();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        pane.getChildren().addAll(hyperlink,button);
    }

    private void uploadFile() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(DataBank.getStage());
        if (selectedFile != null) {
            String filePath = selectedFile.getPath();
            showAlert(filePath);
        }
    }

    private void showAlert(String filePath) {
        DataBank.getCurrentUser().setAvatar(filePath);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Changing avatar");
        alert.setContentText("avatar changed successfully");
        alert.showAndWait();
        DataBank.writeToJson();
    }

    private void setAvatar(String address) {
        showAlert(address);
    }

    private void back() throws Exception {
        new MainMenu().start(DataBank.getStage());
    }
}
