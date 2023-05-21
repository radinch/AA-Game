package view;

import controller.LoginController;
import controller.MainMenuController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.DataBank;
import model.User;

public class SignUpMenuController {//todo choosing avatar
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    private final LoginController loginController = new LoginController();

    public void submit(MouseEvent mouseEvent) throws Exception {
        if(loginController.isUsernameUsed(username.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("submit error");
            alert.setContentText("this username already exits");
            alert.showAndWait();
        }
        else if(!loginController.isPasswordStrong(password.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("submit error");
            alert.setContentText("your password is weak");
            alert.showAndWait();
        }
        else {
            DataBank.getUsers().add(new User(username.getText(),password.getText(),null));
            DataBank.writeToJson();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("sign up successful");
            alert.setContentText("user created now you can login");
            alert.showAndWait();
            new LoginMenu().start(DataBank.getStage());
        }
    }

    public void changeMenuToLogin(MouseEvent mouseEvent) throws Exception {
        new LoginMenu().start(DataBank.getStage());
    }

    public void startGameAsGuest(MouseEvent mouseEvent) throws Exception {
        MainMenuController.prepareGame();
        new Game().start(DataBank.getStage());
    }
}
