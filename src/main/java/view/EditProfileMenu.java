package view;

import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.DataBank;

import java.net.URL;
import java.util.Optional;

public class EditProfileMenu extends Application {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    private final LoginController loginController = new LoginController();
    public void submit(MouseEvent mouseEvent) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Change profile attributes");
        alert.setHeaderText("Edit confirmation");
        alert.setContentText("are you sure with your new profile attributes?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            if(areEditsValid() != 0) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText("Change profile failed");
                if (areEditsValid() == 1)
                    error.setContentText("username already exists");
                else
                    error.setContentText("password is not strong");
                error.showAndWait();
            }
            else {
                DataBank.getCurrentUser().setUsername(username.getText());
                DataBank.getCurrentUser().setPassword(password.getText());
                DataBank.writeToJson();
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText("Edit profile successful");
                successAlert.setContentText("profile successfully changed");
                successAlert.showAndWait();
                new ProfileMenu().start(DataBank.getStage());
            }
        }
    }

    private int areEditsValid() {
        if(loginController.isUsernameUsed(username.getText()) &&
                !username.getText().equals(DataBank.getCurrentUser().getUsername()))
            return 1;
        else if(!loginController.isPasswordStrong(password.getText()))
            return 2;
        return 0;
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL url = LoginMenu.class.getResource("/FXML/editProfileMenu.fxml");
        BorderPane borderPane = FXMLLoader.load (url);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        username.setText(DataBank.getCurrentUser().getUsername());
        password.setText(DataBank.getCurrentUser().getPassword());
    }
}
