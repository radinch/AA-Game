package view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.DataBank;
import model.User;

import java.net.URL;
import java.util.Comparator;

public class Scoreboard extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL url = Scoreboard.class.getResource("/FXML/scoreboard.fxml");
        Pane pane = FXMLLoader.load (url);
        setTable(pane);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private void setTable(Pane pane) {
        TableView table = new TableView<>();
        final Label label = new Label("Scoreboard");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn firstNameCol = new TableColumn("Name");
        firstNameCol.setStyle("-fx-alignment: CENTER");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("username"));
        TableColumn scoreCol = new TableColumn("Score");
        scoreCol.setStyle("-fx-alignment: CENTER");
        scoreCol.setMinWidth(100);
        scoreCol.setCellValueFactory(
                new PropertyValueFactory<User, Integer>("highScore"));
        TableColumn timerCol = new TableColumn("Duration");
        timerCol.setStyle("-fx-alignment: CENTER");
        timerCol.setMinWidth(100);
        timerCol.setCellValueFactory(
                new PropertyValueFactory<User,Integer>("time"));
        ObservableList<User> data = FXCollections.observableArrayList(DataBank.getUsers());
        table.setItems(data);
        table.getColumns().addAll(firstNameCol, scoreCol, timerCol);
        table.getItems().sort(Comparator.comparing(User::getHighScore).reversed()
                .thenComparing(User::getTime));
        VBox vBox = new VBox();
        vBox.setLayoutX(75);
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(label,table);
        pane.getChildren().add(vBox);
    }
}
