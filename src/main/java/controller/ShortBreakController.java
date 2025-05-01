package controller;

import entity.Pomodoro;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class ShortBreakController {

    @FXML
    private Label breakTimerLabel;

    @FXML
    private Button startBreakButton;

    @FXML
    private Button resetButtonShort;

    private int breakTimeRemaining = 300;
    private Timeline breakTimeline;
    private Pomodoro pomodoro; // ✅ Add this line to hold the Pomodoro object
    private boolean isRunning = false;

    @FXML
    public void initialize() {
        updateBreakDisplay();
    }

    public void initializeData(Pomodoro pomodoro, int breakSeconds, boolean autoStart) {
        this.pomodoro = pomodoro;
        this.breakTimeRemaining = breakSeconds;

        updateBreakDisplay();

        if (autoStart) {
            startBreak(); // ✅ Only start if told to
        }
    }

    @FXML
    public void startBreak() {
        if (breakTimeline == null || breakTimeline.getStatus() != Timeline.Status.RUNNING) {
            breakTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                if (breakTimeRemaining > 0) {
                    breakTimeRemaining--;
                    updateBreakDisplay();
                } else {
                    breakTimeline.stop();
                    startBreakButton.setText("Break Done!");
                }
            }));
            breakTimeline.setCycleCount(Timeline.INDEFINITE);
            breakTimeline.play();
            startBreakButton.setText("PAUSE");
            resetButtonShort.setVisible(false);
        } else {
            breakTimeline.stop();
            startBreakButton.setText("RESUME");
            resetButtonShort.setVisible(true);
        }
    }

    @FXML
    private void resetTimerShort(ActionEvent event) {
        // Stop the timer if running
        if (breakTimeline != null) {
            breakTimeline.stop();
        }
        // Reset the timeRemaining back to 10 seconds
        breakTimeRemaining = 300;
        // Update the label display
        updateBreakDisplay();
        // Change the timer button text back to "START"
        startBreakButton.setText("START");
        // Hide the reset button again
        resetButtonShort.setVisible(false);
        // Set running state to false
        isRunning = false;
    }

    private void updateBreakDisplay() {
        int minutes = breakTimeRemaining / 60;
        int seconds = breakTimeRemaining % 60;
        breakTimerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    @FXML
    public void switchToFocus(ActionEvent event) {
        SceneController.switchFocus(event, "/org/example/pomodoroapplication/hello-view.fxml", pomodoro, 300,true);
    }

    public void switchToLongBreak(ActionEvent event) {
        SceneController.switchScene(event, "/org/example/pomodoroapplication/longBreakTime.fxml", pomodoro, 600,false);
    }
}
