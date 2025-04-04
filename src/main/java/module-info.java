module org.example.pomodoroapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens org.example.pomodoroapplication to javafx.fxml;
    exports org.example.pomodoroapplication;
    opens controller to javafx.fxml;
    exports entity;
    opens entity to javafx.fxml;
    exports controller;
}