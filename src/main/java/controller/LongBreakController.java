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

    private int breakTimeRemaining = 900;
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
            startLongBreakButton.setText("Pause");
        } else {
            breakTimeline.stop();
            startLongBreakButton.setText("Resume");
        }
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
