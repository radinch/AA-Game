package view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import model.DataBank;
import model.Map;

import java.util.Optional;

public class MainMenuController {
    private final controller.MainMenuController mainMenuController = new controller.MainMenuController();

    public void startNewGame(MouseEvent mouseEvent) throws Exception {
        controller.MainMenuController.prepareGame();
        new Game().start(DataBank.getStage());
    }

    public void ContinueGame(MouseEvent mouseEvent) {
    }

    public void changeToProfileMenu(MouseEvent mouseEvent) throws Exception {
        new ProfileMenu().start(DataBank.getStage());
    }

    public void showScoreBoard(MouseEvent mouseEvent) {
    }

    public void goToSettings(MouseEvent mouseEvent) throws Exception {
        new Settings().start(DataBank.getStage());
    }

    public void exit(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Exit confirmation");
        alert.setContentText("are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            DataBank.getStage().close();
        }
    }
}
