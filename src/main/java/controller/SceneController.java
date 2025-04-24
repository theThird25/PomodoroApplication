package controller;

import entity.Pomodoro;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.io.IOException;

public class SceneController {

    public static void switchScene(ActionEvent event, String fxmlPath, Pomodoro pomodoro, int breakSeconds, boolean autoStart) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource(fxmlPath));
            Parent root = loader.load();

            // Handle different controllers based on path
            Object controller = loader.getController();
            if (controller instanceof ShortBreakController) {
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

    public static void switchFocus(ActionEvent event, String fxmlPath, Pomodoro pomodoro, int breakSeconds, boolean autoStart) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource(fxmlPath));
            Parent root = loader.load();

            // Handle different controllers based on path
            Object controller = loader.getController();
            if (controller instanceof MainController) {
                ((MainController) controller).setPomodoro(pomodoro);
            } else if (controller instanceof ShortBreakController) {
                ((ShortBreakController) controller).initializeData(pomodoro, breakSeconds, autoStart);
            } else if (controller instanceof LongBreakController) {
                ((LongBreakController) controller).initializeData(pomodoro, breakSeconds, autoStart);
            }

            // Switch the scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}