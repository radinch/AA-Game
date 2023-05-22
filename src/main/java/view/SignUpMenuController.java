package view;

import controller.LoginController;
import controller.MainMenuController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.DataBank;
import model.User;

import java.util.Optional;

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
            int random_int = (int) Math.floor(Math.random() * 6 + 1);
            User user = new User(username.getText(),password.getText(), null);
            DataBank.getUsers().add(user);
            DataBank.writeToJson();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Choose avatar");
            alert.setContentText("do you want to choose avatar?");
            Optional<ButtonType> result = alert.showAndWait();
            DataBank.setCurrentUser(DataBank.getUserByUsername(username.getText()));
            if(result.get() == ButtonType.OK) {
                new AvatarMenu().start(DataBank.getStage());
            }
            else {
                DataBank.getCurrentUser().setAvatar((SignUpMenuController.class.getResource("/IMAGE/" + random_int+".png").toExternalForm()));
                new MainMenu().start(DataBank.getStage());
            }
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
