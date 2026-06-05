package com.demo.gui.controller;

import com.demo.core.logic.GameEngine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InGameController implements Initializable {
    private Stage stage;
    private Scene scene;
    private GameEngine gameEngine;
    private byte usedHelpsCounter = 0;
    @FXML
    private Label helpDisplayLabel;
    @FXML
    private RadioButton fiftyRadioBtn;
    @FXML
    private RadioButton phoneRadioBtn;
    @FXML
    private RadioButton audienceRadioBtn;
    @FXML
    private ListView helpsListView;
    @FXML
    private Button showHelpResultBtn;
    @FXML
    private Label helpLabel;
    @FXML
    private Label questionLabel;
    @FXML
    private Button resetHelpsBtn;

    public void setGameEngine(GameEngine sharedGameEngine) {
        this.gameEngine = sharedGameEngine;
        this.questionLabel.setText(this.gameEngine.getPlayerName());
    }

    public void submit() {
        System.out.println("submit");
    }

    public void backToMenu(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Quit Game");
        alert.setContentText("Are you sure you want to quit the game?\nGame progress will be lost.");
        if (alert.showAndWait().get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/com/demo/gui/menu.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void showHelpResult() {
        if (fiftyRadioBtn.isSelected()) {
            fiftyRadioBtn.setVisible(false);
            helpDisplayLabel.setText("Displaying 50:50 result");
        } else if (phoneRadioBtn.isSelected()) {
            phoneRadioBtn.setVisible(false);
            helpDisplayLabel.setText("Displaying phone result");
        } else if (audienceRadioBtn.isSelected()) {
            audienceRadioBtn.setVisible(false);
            helpDisplayLabel.setText("Displaying audience result");
        }
        usedHelpsCounter++;
        helpDisplayLabel.setVisible(true);

        if (usedHelpsCounter == 3) {
            helpsListView.setVisible(false);
            showHelpResultBtn.setVisible(false);
            helpDisplayLabel.setLayoutY(helpDisplayLabel.getLayoutY() - 110);
            helpDisplayLabel.setText("You have used up all of the available helps!");
            helpLabel.setVisible(false);
        }
        showHelpResultBtn.setDisable(true);
    }

    public void enableShowResultBtn() {
        resetHelpsBtn.setDisable(false);
        showHelpResultBtn.setDisable(false);
    }

    public void resetHelps() {
        resetHelpsBtn.setDisable(true);
        audienceRadioBtn.setSelected(false);
        phoneRadioBtn.setSelected(false);
        fiftyRadioBtn.setSelected(false);
        showHelpResultBtn.setDisable(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
