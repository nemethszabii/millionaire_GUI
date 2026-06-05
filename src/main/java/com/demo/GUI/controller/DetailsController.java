package com.demo.gui.controller;

import com.demo.core.logic.GameEngine;
import com.demo.core.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class DetailsController {
    @FXML
    private TextField usernameLabel;
    private Stage stage;
    private Scene scene;
    private GameEngine gameEngine;

    public void continueToGame(ActionEvent event) throws IOException {
        if (usernameLabel.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Username can not be empty!");
            alert.setContentText("You must enter a username!");
            alert.show();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/demo/gui/inGame.fxml"));
            Parent root = loader.load();
            InGameController inGameController = loader.getController();
            this.gameEngine = new GameEngine(usernameLabel.getText()); // SET UP GAME ENGINE
            inGameController.setGameEngine(this.gameEngine); // PASS SHARED GAME ENGINE TO THE NEXT CONTROLLER
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show(); // SHOW NEXT SCENE (IN-GAME)
        }
    }
}
