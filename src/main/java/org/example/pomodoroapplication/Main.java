package org.example.pomodoroapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        String css = Objects.requireNonNull(getClass().getResource("/css/styles.css")).toExternalForm();
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/PomoductiveLogo.png"))));
        stage.setTitle("Pomoductive");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}