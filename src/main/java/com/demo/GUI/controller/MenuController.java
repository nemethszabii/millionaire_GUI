package com.demo.gui.controller;

import com.demo.core.logic.GameEngine;
import com.demo.core.logic.Leaderboard;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    @FXML
    private AnchorPane menuPane;
    @FXML
    private Button startBtn, leaderboardBtn, quitBtn;
    private Stage stage;
    private Scene scene;
    private GameEngine gameEngine;
    private Leaderboard leaderboard;

    public MenuController() {
        this.gameEngine = new GameEngine();
        this.leaderboard = this.gameEngine.getLeaderboard();
    }

    public void startGame(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/demo/gui/details.fxml"));
        Parent root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showLeaderboard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/demo/gui/leaderboard.fxml"));
        Parent root = loader.load();
        LeaderboardController leaderboardController = loader.getController();
        leaderboardController.setLeaderboard(leaderboard);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
