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

import java.util.Objects;

public class LoginMenuController {
    private final LoginController loginController = new LoginController();
    @FXML
    private PasswordField password;
    @FXML
    private TextField username;

    public void submit(MouseEvent mouseEvent) throws Exception { //todo start game with guest mode
        if(!loginController.doesUserExists(username.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("submit error");
            alert.setContentText("username not found");
            alert.showAndWait();
        }
        else if(!loginController.isPasswordCorrect(password.getText(),
                Objects.requireNonNull(DataBank.getUserByUsername(username.getText())))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("submit error");
            alert.setContentText("password is not correct");
            alert.showAndWait();
        }
        else {
            DataBank.setCurrentUser(DataBank.getUserByUsername(username.getText()));
            new MainMenu().start(DataBank.getStage());
        }
    }

    public void changeMenuToSignUp(MouseEvent mouseEvent) throws Exception {
        new SignUpMenu().start(DataBank.getStage());
    }

    public void startGameAsGuest(MouseEvent mouseEvent) throws Exception {
        MainMenuController.prepareGame();
        new Game().start(DataBank.getStage());
    }
}
