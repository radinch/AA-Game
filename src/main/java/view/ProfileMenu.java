package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Avatar;
import model.DataBank;

import java.net.URL;

public class ProfileMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/profileMenu.fxml");
        BorderPane borderPane = FXMLLoader.load (url);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(new Avatar(200,100,DataBank.getCurrentUser().getAvatar()));
        borderPane.setTop(vBox);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }
}
