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

    private int breakTimeRemaining = 300;
    private Timeline breakTimeline;
    private Pomodoro pomodoro; // ✅ Add this line to hold the Pomodoro object

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
            startBreakButton.setText("Pause");
        } else {
            breakTimeline.stop();
            startBreakButton.setText("Resume");
        }
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
}