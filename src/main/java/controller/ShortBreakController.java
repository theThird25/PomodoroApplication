package controller;

import entity.Pomodoro;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class ShortBreakController {

    @FXML
    private Label breakTimerLabel; // This will display the timer.

    @FXML
    private Button startBreakButton; // This button will start/pause the timer.

    private int breakTimeRemaining = 300; // 5 minutes in seconds
    private Timeline breakTimeline;

    @FXML
    public void initialize() {
        updateBreakDisplay(); // Update display initially.
    }

    public void initializeData(Pomodoro pomodoro, int breakSeconds) {
        this.breakTimeRemaining = breakSeconds; // Set break time from the previous scene.
        updateBreakDisplay();  // Show the correct time immediately.
    }

    @FXML
    public void startBreak() {
        if (breakTimeline == null || breakTimeline.getStatus() != Timeline.Status.RUNNING) {
            // Start a new timeline for the countdown.
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
            startBreakButton.setText("Pause");
        } else {
            breakTimeline.stop();
            startBreakButton.setText("Resume");
        }
    }

    private void updateBreakDisplay() {
        int minutes = breakTimeRemaining / 60;
        int seconds = breakTimeRemaining % 60;
        breakTimerLabel.setText(String.format("%02d:%02d", minutes, seconds)); // Display formatted time
    }
}