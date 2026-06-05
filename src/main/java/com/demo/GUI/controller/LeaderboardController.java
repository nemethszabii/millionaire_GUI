package com.demo.gui.controller;

import com.demo.core.logic.Leaderboard;
import com.demo.core.model.Player;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class LeaderboardController {
    private Stage stage;
    private Scene scene;
    private Leaderboard leaderboard;
    private List<Player> playerList;
    @FXML
    private TableView<Player> playerTableView;
    private TableColumn<Player, Integer> rankColumn;
    private TableColumn<Player, String> nameColumn;
    private TableColumn<Player, Integer> scoreColumn;
    private TableColumn<Player, String> timeColumn;

    public void setLeaderboard(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
        this.playerList = this.leaderboard.getLeaderboard();

        playerTableView.setItems(FXCollections.observableArrayList(playerList));

        TableColumn<Player, Integer> rankColumn = new TableColumn<Player, Integer>("Rank");
        TableColumn<Player, String> nameColumn = new TableColumn<Player, String>("Name");
        TableColumn<Player, Integer> scoreColumn = new TableColumn<Player, Integer>("Prize");
        TableColumn<Player, String> timeColumn = new TableColumn<Player, String>("Time");

        rankColumn.setPrefWidth(50);
        nameColumn.setPrefWidth(100);
        scoreColumn.setPrefWidth(100);
        timeColumn.setPrefWidth(148);

        rankColumn.setStyle("-fx-alignment: CENTER;");
        nameColumn.setStyle("-fx-alignment: CENTER;");
        scoreColumn.setStyle("-fx-alignment: CENTER;");
        timeColumn.setStyle("-fx-alignment: CENTER;");

        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("prize"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        playerTableView.getColumns().setAll(rankColumn, nameColumn, scoreColumn, timeColumn);
    }

    public void backToMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/demo/gui/menu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
