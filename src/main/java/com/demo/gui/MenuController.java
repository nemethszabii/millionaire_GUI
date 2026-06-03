package com.demo.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController {
    @FXML
    private AnchorPane menuPane;
    @FXML
    private Button startBtn;
    @FXML
    private Button leaderboardBtn;
    @FXML
    private Button quitBtn;
    private Stage stage;

    public void startGame(ActionEvent event) {
        System.out.println("Start game");
    }

    public void showLeaderboard(ActionEvent event) {
        System.out.println("Leaderboard");
    }

    public void quitGame() {
        stage = (Stage)menuPane.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit program");
        alert.setHeaderText("You are about to exit!");
        alert.setContentText("Are you sure you want to exit?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            stage.close();
        }
    }
}
