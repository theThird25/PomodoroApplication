package controller;

import entity.Pomodoro;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class BreakTimeController implements Initializable {

    @FXML
    private Text pomTimer;

    private Timeline timeline;
    private Pomodoro pomodoro;
    private int breakDurationSeconds;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Basic initialization if needed
    }

    public void initializeData(Pomodoro pomodoro, int breakDurationSeconds) {
        this.pomodoro = pomodoro != null ? pomodoro : new Pomodoro(breakDurationSeconds);
        this.breakDurationSeconds = breakDurationSeconds;
        resetTimer();
        startBreakCountdown();
    }

    private void startBreakCountdown() {
        if (timeline != null) {
            timeline.stop();
        }

        // Corrected code: Added missing closing parenthesis
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            int seconds = pomodoro.getSeconds() - 1;
            pomodoro.setSeconds(seconds);
            updateTimerDisplay(seconds);

            if (seconds <= 0) {
                stopTimer();
                playAlarm();
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    private void stopTimer() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    private void resetTimer() {
        stopTimer();
        pomodoro.setSeconds(breakDurationSeconds);
        updateTimerDisplay(breakDurationSeconds);
    }

    private void updateTimerDisplay(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        pomTimer.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void playAlarm() {
        try {
            // Try alternative path format
            AudioClip alarm = new AudioClip(
                    getClass().getClassLoader().getResource("audio/BellDing.mp3").toString());
            alarm.play();
        } catch (Exception e) {
            System.err.println("Couldn't play alarm sound");
            Toolkit.getDefaultToolkit().beep(); // Fallback system beep
        }
    }

    @FXML
    public void switchToMainTimer(ActionEvent event) {
        stopTimer();
        SceneController.switchScene(event, "/fxml/hello-view.fxml", pomodoro);
    }
}
