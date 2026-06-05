package com.demo.gui.controller;

import com.demo.core.logic.GameEngine;
import com.demo.core.model.Player;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailsController implements Initializable {
    @FXML
    private TextField usernameLabel;
    private Stage stage;
    private Scene scene;
    private GameEngine gameEngine;

    public void continueToGame(Event event) throws IOException {
        if (usernameLabel.getText().isEmpty() || usernameLabel.getText().length() < 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Username Error!");
            alert.setContentText("Enter a username with min. 3 characters!");
            alert.show();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/demo/gui/inGame.fxml"));
            Parent root = loader.load();
            InGameController inGameController = loader.getController();
            this.gameEngine = new GameEngine(); // SET UP GAME ENGINE
            this.gameEngine.setPlayerName(usernameLabel.getText());
            inGameController.setGameEngine(this.gameEngine); // PASS SHARED GAME ENGINE TO THE NEXT CONTROLLER
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show(); // SHOW NEXT SCENE (IN-GAME)
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameLabel.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    continueToGame(event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
