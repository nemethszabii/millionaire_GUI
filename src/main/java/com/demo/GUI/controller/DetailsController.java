package com.demo.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class DetailsController {
    @FXML
    private TextField usernameLabel;
    private Stage stage;
    private Scene scene;

    public void continueToGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/demo/gui/inGame.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
