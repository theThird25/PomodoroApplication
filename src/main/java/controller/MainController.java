package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class MainController {

    @FXML
    private Button timerButton;

    private int timeRemaining = 25 * 60; // 25 minutes in seconds
    private Timeline timeline;

    @FXML
    public void initialize() {
        if (timerButton == null) {
            System.out.println("timerButton is null! Check FXML file.");
        } else {
            updateTimerDisplay(); // Set "25:00" as the initial text
        }
    }

    @FXML
    public void startTime() {
        if (timeline == null || timeline.getStatus() != Timeline.Status.RUNNING) {
            timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                if (timeRemaining > 0) {
                    timeRemaining--;
                    updateTimerDisplay();
                } else {
                    timeline.stop();
                    timerButton.setText("Done!");
                }
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    }

    private void updateTimerDisplay() {
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;
        timerButton.setText(String.format("%02d:%02d", minutes, seconds));
    }
}