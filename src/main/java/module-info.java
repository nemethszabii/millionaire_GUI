module com.demo.hellofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.demo.core.model to javafx.base;

    opens com.demo.gui to javafx.fxml;
    exports com.demo.gui;
    exports com.demo.gui.controller;
    opens com.demo.gui.controller to javafx.fxml;
}