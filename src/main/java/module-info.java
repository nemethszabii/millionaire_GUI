module com.demo.hellofx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.demo.gui to javafx.fxml;
    exports com.demo.gui;
    exports com.demo.gui.controller;
    opens com.demo.gui.controller to javafx.fxml;
}