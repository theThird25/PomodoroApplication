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

    public static void switchScene(ActionEvent event, String fxmlPath, Pomodoro pomodoro) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource(fxmlPath));
            Parent root = loader.load();

            // Get the controller
            Object controller = loader.getController();

            if (controller instanceof MainController mainController) {
                mainController.initialize();
            } else if (controller instanceof BreakTimeController breakController) {
                breakController.initializeData(pomodoro, 300); // default to 5 min
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Switches to a break scene and passes both Pomodoro and custom break time.
     */

    public static void switchSceneWithBreak(ActionEvent event, String fxmlPath, Pomodoro pomodoro, int breakSeconds) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource(fxmlPath));
            Parent root = loader.load();

            BreakTimeController controller = loader.getController();
            controller.initializeData(pomodoro, breakSeconds);  // method to initialize break time in BreakTimeController

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}