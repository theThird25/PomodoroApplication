package controller;

import entity.Pomodoro;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

public class MainController {

    @FXML
    private Label timerLabel;

    @FXML
    private Button timerButton;

    private int timeRemaining = 25 * 60; // 25 minutes in seconds
    private Timeline timeline;
    private Pomodoro pomodoro;
    private boolean isRunning = false;

    private final int INITIAL_TIME = 25 * 60; // 25 minutes

    @FXML
    public void initialize() {
        if (timerButton == null) {
            System.out.println("timerButton is null! Check FXML file.");
        } else {
            updateTimerDisplay(); // Set "25:00" as the initial text
        }
    }

    @FXML
    public void startTimer() {
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

    private void pauseTimer() {
        if (timeline != null) {
            timeline.pause();
        }
    }

    @FXML
    public void resetTime(ActionEvent event) {
        if (timeline != null) {
            timeline.stop();
        }
        pomodoro.setSeconds(INITIAL_TIME);
        timerButton.setText("START");
        isRunning = false;
    }

    @FXML
    public void switchToBreak(ActionEvent event) {
        SceneController.switchSceneWithBreak(event, "/fxml/shortBreakTime.fxml", pomodoro, 300);
    }

    private void updateTimerDisplay() {
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void playAlarm() {
        AudioClip alarm = new AudioClip(getClass().getResource("/audio/BellDing.mp3").toString());
        alarm.play();
    }
}