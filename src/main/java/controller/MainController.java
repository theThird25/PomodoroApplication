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



    private int timeRemaining = 10; // 25 minutes in seconds 25 * 60
    // changed the timer to 10 seconds just for this time
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

    public void setPomodoro(Pomodoro pomodoro) {
        this.pomodoro = pomodoro;

    }

    private void playAlarm() {
        AudioClip alarm = new AudioClip(getClass().getResource("/Sounds/Ding.wav").toString());
        alarm.play();
    }

    @FXML
    public void startTimer() {
        if (timeline == null || !timeline.getStatus().equals(Timeline.Status.RUNNING)) {
            // Start the timer
            timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                if (timeRemaining > 0) {
                    timeRemaining--;
                    updateTimerDisplay();
                } else {
                    timeline.stop();
                    timerButton.setText("Done"); // Reset label when done
                    playAlarm();

                    SceneController.switchFocus(
                            new ActionEvent(timerButton, null),
                            "/org/example/pomodoroapplication/shortBreakTime.fxml",
                            pomodoro,
                            300,
                            true// ✅ autoStart enabled from auto transition
                    );

                }
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
            timerButton.setText("Pause"); // <<< change to PAUSE
        } else {
            // Pause the timer
            timeline.stop();
            timerButton.setText("Resume"); // <<< change back to START
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



    private void updateTimerDisplay() {
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    @FXML
    public void switchToBreak(ActionEvent event) {
        SceneController.switchScene(event, "/org/example/pomodoroapplication/shortBreakTime.fxml", pomodoro, 300,false);
    }

    public void switchToLongBreak(ActionEvent event) {
        SceneController.switchScene(event, "/org/example/pomodoroapplication/longBreakTime.fxml", pomodoro, 600,false);
    }

}