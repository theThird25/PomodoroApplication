package controller;

import entity.Pomodoro;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class LongBreakController {
    @FXML
    private Label breakLongTimerLabel;

    @FXML
    private Button startLongBreakButton;

    @FXML
    private Button resetButtonLong;

    private int breakTimeRemaining = 900;
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
            startLongBreak(); // ✅ Only start if told to
        }
    }

    @FXML
    public void startLongBreak() {
        if (breakTimeline == null || breakTimeline.getStatus() != Timeline.Status.RUNNING) {
            breakTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                if (breakTimeRemaining > 0) {
                    breakTimeRemaining--;
                    updateBreakDisplay();
                } else {
                    breakTimeline.stop();
                    startLongBreakButton.setText("Break Done!");
                }
            }));
            breakTimeline.setCycleCount(Timeline.INDEFINITE);
            breakTimeline.play();
            startLongBreakButton.setText("PAUSE");
            resetButtonLong.setVisible(false);
        } else {
            breakTimeline.stop();
            startLongBreakButton.setText("RESUME");
            resetButtonLong.setVisible(true);
        }
    }

    @FXML
    private void resetTimerLong(ActionEvent event) {
        // Stop the timer if running
        if (breakTimeline != null) {
            breakTimeline.stop();
        }
        // Reset the timeRemaining back to 10 seconds
        breakTimeRemaining = 600;
        // Update the label display
        updateBreakDisplay();
        // Change the timer button text back to "START"
        startLongBreakButton.setText("START");
        // Hide the reset button again
        resetButtonLong.setVisible(false);
        // Set running state to false
        isRunning = false;
    }

    private void updateBreakDisplay() {
        int minutes = breakTimeRemaining / 60;
        int seconds = breakTimeRemaining % 60;
        breakLongTimerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    @FXML
    public void switchToFocus(ActionEvent event) {
        SceneController.switchFocus(event, "/org/example/pomodoroapplication/hello-view.fxml", pomodoro, 900,false);
    }

    @FXML
    public void switchToBreak(ActionEvent event) {
        SceneController.switchScene(event, "/org/example/pomodoroapplication/shortBreakTime.fxml", pomodoro, 300,false);
    }
}
