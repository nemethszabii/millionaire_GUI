package com.demo.gui.controller;

import com.demo.core.logic.GameEngine;
import com.demo.core.model.Question;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class InGameController {
    private Stage stage;
    private Scene scene;
    private GameEngine gameEngine;
    private Question currentQuestion;
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
    @FXML
    private ToggleGroup choiceGroup;
    @FXML
    private RadioButton choice1;
    @FXML
    private RadioButton choice2;
    @FXML
    private RadioButton choice3;
    @FXML
    private RadioButton choice4;

    public void setGameEngine(GameEngine sharedGameEngine) {
        this.gameEngine = sharedGameEngine;
        this.currentQuestion = gameEngine.getCurrentQuestionObj();
        setUpInGameContent();
    }

    private void setUpInGameContent() {
        this.questionLabel.setText(this.currentQuestion.getQuestion());
        List<String> choices = this.currentQuestion.getRandomOrderedAnswers();
        this.choice1.setText(choices.get(0));
        this.choice2.setText(choices.get(1));
        this.choice3.setText(choices.get(2));
        this.choice4.setText(choices.get(3));
    }

    public void onSubmit(ActionEvent event) throws IOException {
        RadioButton selected = (RadioButton)choiceGroup.getSelectedToggle();
        String val = selected.getText();
        if (this.currentQuestion.getAnswer().equals(val)) {
            this.gameEngine.incrementQuestionCounter();
            this.currentQuestion = this.gameEngine.getCurrentQuestionObj();
            setUpInGameContent();
        } else {
            handleLose(event);
        }
    }

    private void handleLose(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("GAME OVER");
        alert.setHeaderText("Your answer is wrong!");
        alert.setContentText("You are being redirected to the menu.");
        redirectToMenu(event, alert);
    }

    public void backToMenu(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Quit Game");
        alert.setContentText("Are you sure you want to quit the game?\nGame progress will be lost.");
        redirectToMenu(event, alert);
    }

    private void redirectToMenu(Event event, Alert alert) throws IOException {
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
        resetHelpsBtn.setDisable(true);
        showHelpResultBtn.setDisable(true);
    }

    public void enableHelpActionButtons() {
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
}
