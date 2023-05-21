module HW {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;

    exports model;
    opens model to com.fasterxml.jackson.core;
    exports view;
    opens view to javafx.fxml;
}