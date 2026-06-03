module com.demo.hellofx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.demo.gui to javafx.fxml;
    exports com.demo.gui;
}