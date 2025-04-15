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

    public static void switchScene(ActionEvent event, String fxmlPath, Pomodoro pomodoro, int breakSeconds) {
        try {
            // Load the short break FXML and controller
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/org/example/pomodoroapplication/shortBreakTime.fxml"));
            Parent root = loader.load();

            // Initialize ShortBreakController with data
            ShortBreakController controller = loader.getController();
            controller.initializeData(pomodoro, breakSeconds);

            // Switch the scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}