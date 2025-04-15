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

    public static void switchScene(ActionEvent event, String fxmlPath, Pomodoro pomodoro, int breakSeconds,boolean autoStart) {
        try {
            // Load the short break FXML and controller
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/org/example/pomodoroapplication/shortBreakTime.fxml"));
            Parent root = loader.load();

            // Initialize ShortBreakController with data
            ShortBreakController controller = loader.getController();
            controller.initializeData(pomodoro, breakSeconds,autoStart);

            // Switch the scene
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

            // Pass the pomodoro object to the correct controller
            if (fxmlPath.equals("/org/example/pomodoroapplication/hello-view.fxml")) {
                // MainController: Set the pomodoro object
                MainController controller = loader.getController();
                controller.setPomodoro(pomodoro);
            } else if (fxmlPath.equals("/org/example/pomodoroapplication/shortBreakTime.fxml")) {
                ShortBreakController controller = loader.getController();
                controller.initializeData(pomodoro, breakSeconds,autoStart); // pass autoStart here ✅
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