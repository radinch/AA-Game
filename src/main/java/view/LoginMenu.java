package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.DataBank;

import java.net.URL;

public class LoginMenu extends Application {
    public static void main(String[] args) {
        Application.launch(LoginMenu.class,args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        DataBank.setStage(stage);
        DataBank.setUsers(DataBank.readFromJson());
        URL url = LoginMenu.class.getResource("/FXML/loginMenu.fxml");
        BorderPane borderPane = FXMLLoader.load (url);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }
}