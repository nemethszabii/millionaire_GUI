package com.demo.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class GuiApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent menuRoot = FXMLLoader.load(getClass().getResource("/com/demo/gui/menu.fxml"));
        Scene menuScene = new Scene(menuRoot, 600, 500);

        stage.setResizable(false);

        Image img = new Image(getClass().getResourceAsStream("/com/demo/gui/assets/main-icon.png"));
        stage.getIcons().add(img);

        stage.setTitle("Who wants to be a millionaire?");
        stage.setScene(menuScene);
        stage.show();
    }
}
