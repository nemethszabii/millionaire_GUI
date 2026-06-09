package com.demo.gui.controller;

import com.demo.core.logic.GameEngine;
import com.demo.core.logic.Leaderboard;
import com.demo.core.model.Player;
import com.demo.core.model.Question;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class InGameController {
    private Stage stage;
    private Scene scene;
    private GameEngine gameEngine;
    private Player player;
    private Question currentQuestion;
    private byte usedHelpsCounter = 0;
    private byte counter = 1;
    private boolean fiftyWasJustUsed;
    private Leaderboard leaderboard;
    @FXML
    private ListView helpsListView;
    @FXML
    private Button submitBtn, showHelpResultBtn, resetHelpsBtn;
    @FXML
    private ToggleGroup choiceGroup;
    @FXML
    private RadioButton fiftyRadioBtn, phoneRadioBtn, audienceRadioBtn, choice1, choice2,  choice3, choice4;
    @FXML
    private Label helpDisplayLbl, questionLbl, helpLbl, prizeLbl, securePrizeLbl, nextPrizeLbl;

    public void setGameEngine(GameEngine sharedGameEngine) {
        this.gameEngine = sharedGameEngine;
        this.leaderboard = this.gameEngine.getLeaderboard();
        this.player = this.gameEngine.getPlayer();
        this.currentQuestion = gameEngine.getCurrentQuestionObj();
        setUpInGameContent();
    }

    private void setUpInGameContent() {
        this.player.startTimer();
        this.helpDisplayLbl.setVisible(false);
        this.questionLbl.setText(this.gameEngine.getQuestionCounter() + 1 + ". " + this.currentQuestion.getQuestion());
        List<String> choices = this.currentQuestion.getRandomOrderedAnswers();

        if (fiftyWasJustUsed) {
            this.choice1.setVisible(true);
            this.choice4.setVisible(true);
            this.fiftyWasJustUsed = false;
        }

        if (usedHelpsCounter == 3) {
            hideHelpOptions();
        }

        this.choice1.setText(choices.get(0));
        this.choice2.setText(choices.get(1));
        this.choice3.setText(choices.get(2));
        this.choice4.setText(choices.get(3));

        this.prizeLbl.setText(this.player.getPrize());
        this.securePrizeLbl.setText(this.player.getSecurePrize());
        this.nextPrizeLbl.setText(this.player.getNextPrize());
    }

    private void hideHelpOptions() {
        helpsListView.setVisible(false);
        resetHelpsBtn.setVisible(false);
        showHelpResultBtn.setVisible(false);
        helpDisplayLbl.setLayoutY(helpDisplayLbl.getLayoutY() - 110);
        helpDisplayLbl.setText("You have used up all of the available helps!");
        helpLbl.setVisible(false);
        this.usedHelpsCounter = 0;
    }

    public void onSubmit(ActionEvent event) throws IOException {
        RadioButton selectedRb = (RadioButton)choiceGroup.getSelectedToggle();
        String val = selectedRb.getText();
        if (this.currentQuestion.getAnswer().equals(val)) {
            if (this.counter < 15) {
                this.gameEngine.incrementQuestionCounter();
                this.gameEngine.incrementPlayerPrize();
                this.currentQuestion = this.gameEngine.getCurrentQuestionObj();
                setUpInGameContent();
                this.counter++;
            } else {
                this.player.stopTimer();
                this.leaderboard.addPlayerToLeaderboard(this.player);
                this.leaderboard.write();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Congratulations!");
                alert.setHeaderText("You've just won the game!");
                alert.setContentText("Your knowledge is supreme!\nYour result is written to the leaderboard.");
                redirectToMenu(event, alert);
            }
            this.submitBtn.setDisable(true);
        } else {
            handleLose(event);
        }
        selectedRb.setSelected(false);
    }

    public void enableSubmitBtn(ActionEvent event) throws IOException { this.submitBtn.setDisable(false); }

    private void handleLose(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("GAME OVER");
        alert.setHeaderText("Your answer is wrong!");
        alert.setContentText("You are being redirected to the menu.");
        redirectToMenu(event, alert);
    }

    public void quit(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Quit Game");
        alert.setContentText("Are you sure you want to quit the game?\nGame progress will be lost.");
        redirectToMenu(event, alert);
    }

    private void redirectToMenu(Event event, Alert alert) throws IOException {
        if (alert.showAndWait().get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/demo/gui/menu.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void showHelpResult() {
        if (fiftyRadioBtn.isSelected()) {
            fiftyRadioBtn.setVisible(false);
            String[] resultOfFiftyFifty = this.gameEngine.getHelp().fiftyFifty(this.currentQuestion);
            this.choice1.setVisible(false);
            this.choice4.setVisible(false);
            this.choice2.setText(resultOfFiftyFifty[0]);
            this.choice3.setText(resultOfFiftyFifty[1]);
            this.fiftyWasJustUsed = true;
        } else if (phoneRadioBtn.isSelected()) {
            phoneRadioBtn.setVisible(false);
            helpDisplayLbl.setText("Phone: " + this.gameEngine.getHelp().phone(this.currentQuestion));
            helpDisplayLbl.setVisible(true);
        } else if (audienceRadioBtn.isSelected()) {
            audienceRadioBtn.setVisible(false);
            if (fiftyWasJustUsed) {
                helpDisplayLbl.setText(this.gameEngine.getHelp().audience(this.currentQuestion, true));
            } else {
                helpDisplayLbl.setText(this.gameEngine.getHelp().audience(this.currentQuestion));
            }
            helpDisplayLbl.setVisible(true);
        }
        usedHelpsCounter++;
        resetHelpsBtn.setDisable(true);
        showHelpResultBtn.setDisable(true);
    }

    public void enableHelpActionButtons(ActionEvent event) {
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
