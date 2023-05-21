package view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import model.DataBank;

import java.util.Optional;

public class ProfileMenuController { //todo choose and changing avatar
    public void editProfile(MouseEvent mouseEvent) throws Exception {
        new EditProfileMenu().start(DataBank.getStage());
    }

    public void logout(MouseEvent mouseEvent) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Logout confirmation");
        alert.setContentText("are you sure you want to logout?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            DataBank.setCurrentUser(null);
            new LoginMenu().start(DataBank.getStage());
        }
    }

    public void deleteAccount(MouseEvent mouseEvent) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Account");
        alert.setHeaderText("Delete account confirmation");
        alert.setContentText("are you sure you want to delete account?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            DataBank.getUsers().remove(DataBank.getCurrentUser());
            DataBank.writeToJson();
            DataBank.setCurrentUser(null);
            new LoginMenu().start(DataBank.getStage());
        }
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new MainMenu().start(DataBank.getStage());
    }
}
