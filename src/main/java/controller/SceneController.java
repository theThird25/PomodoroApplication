package controller;

import entity.Pomodoro;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {

    private static final String BASE_PATH = "/org/example/pomodoroapplication/";

    public static void switchScene(ActionEvent event, String fxmlFile, Pomodoro pomodoro,
                                   int breakSeconds, boolean autoStart) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource(BASE_PATH + fxmlFile));
            Parent root = loader.load();

            Object controller = loader.getController();
            if (controller instanceof MainController) {
                ((MainController) controller).setPomodoro(pomodoro);
            } else if (controller instanceof ShortBreakController) {
                ((ShortBreakController) controller).initializeData(pomodoro, breakSeconds, autoStart);
            } else if (controller instanceof LongBreakController) {
                ((LongBreakController) controller).initializeData(pomodoro, breakSeconds, autoStart);
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}