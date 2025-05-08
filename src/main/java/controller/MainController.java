package controller;

import entity.Pomodoro;
import entity.Task;
import javafx.collections.FXCollections;
import view.TaskCell;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    // Time constants in seconds
    private static final int FOCUS_TIME = 25 * 60;    // 25 minutes
    private static final int SHORT_BREAK = 5 * 60;    // 5 minutes
    private static final int LONG_BREAK = 10 * 60;    // 10 minutes

    @FXML
    private Label timerLabel;

    @FXML
    private Button timerButton;

    // Task List Components
    @FXML
    private TextField taskInput;

    @FXML
    private ListView<Task> taskListView;

    @FXML
    private Label counterLabel;

    // Task Management Components
    private ObservableList<Task> tasks;
    private String taskFilePath = "tasks.dat";

    // Time Variables
    private int timeRemaining = FOCUS_TIME;
    private Timeline timeline;
    private Pomodoro pomodoro;
    private boolean isRunning = false;

    @FXML
    public void initialize() {
        tasks = FXCollections.observableArrayList();
        taskListView.setItems(tasks);

        taskListView.setCellFactory(param -> new TaskCell(task -> {
            saveTasks();       // Save when checkbox changes
            updateCounter();   // Update counter display
        }));

        taskListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // 4. Load saved tasks and update display
        loadTasks();
        updateTimerDisplay();
    }

    public void setPomodoro(Pomodoro pomodoro) {
        this.pomodoro = pomodoro;
    }

    @FXML
    public void startTimer(ActionEvent event) {
        if (timeline == null || timeline.getStatus() != Timeline.Status.RUNNING) {
            startTimeline();
            timerButton.setText("PAUSE");
            isRunning = true;
        } else {
            pauseTimer();
            timerButton.setText("RESUME");
            isRunning = false;
        }
    }

    private void startTimeline() {
        if (timeline != null) {
            timeline.stop();
        }

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (timeRemaining > 0) {
                timeRemaining--;
                updateTimerDisplay();
            } else {
                timerComplete();
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void timerComplete() {
        timeline.stop();
        timerButton.setText("DONE");
        playAlarm();

        // Switch to short break automatically when timer completes
        SceneController.switchScene(
                new ActionEvent(timerButton, null),
                "shortBreakTime.fxml",
                pomodoro,
                SHORT_BREAK,
                true  // Auto-start the break timer
        );
    }

    @FXML
    public void handleFocusButton(ActionEvent event) {
        if (isRunning) {
            pauseTimer();
        }
        SceneController.switchScene(
                event,
                "hello-view.fxml",
                pomodoro,
                FOCUS_TIME,
                false
        );
    }

    @FXML
    public void handleShortBreakButton(ActionEvent event) {
        if (isRunning) {
            pauseTimer();
        }
        SceneController.switchScene(
                event,
                "shortBreakTime.fxml",
                pomodoro,
                SHORT_BREAK,
                false  // Don't auto-start
        );
    }

    @FXML
    public void handleLongBreakButton(ActionEvent event) {
        if (isRunning) {
            pauseTimer();
        }
        SceneController.switchScene(
                event,
                "longBreakTime.fxml",
                pomodoro,
                LONG_BREAK,
                false  // Don't auto-start
        );
    }

    private void pauseTimer() {
        if (timeline != null) {
            timeline.pause();
        }
    }

    private void updateTimerDisplay() {
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void playAlarm() {
        try {
            AudioClip alarm = new AudioClip(getClass().getResource("/sounds/Ding.wav").toString());
            alarm.play();
        } catch (Exception e) {
            System.err.println("Error playing alarm sound: " + e.getMessage());
        }
    }

    // Task List Methods
    @FXML
    public void addTask(ActionEvent event) {
        String taskText = taskInput.getText().trim();
        if(!taskText.isEmpty()) {
            Task newTask = new Task(taskText, false);
            tasks.add(newTask);
            taskInput.clear();
            saveTasks();
            updateCounter();

        }
    }

    // Remove checked tasks
    @FXML
    public void removeSelectedTask(ActionEvent event) {
        List<Task> checkedTasks = tasks.stream()
                .filter(Task::isCompleted)
                .toList();

        if (!checkedTasks.isEmpty()) {
            tasks.removeAll(checkedTasks);
            saveTasks();
            updateCounter();
        } else {
            showAlert("Please check the task you want to remove.");
        }
    }

    // Clear completed tasks
    @FXML
    public void clearCompletedTasks(ActionEvent event) {
        List<Task> checkedTasks = tasks.stream()
                .filter(Task::isCompleted)
                .toList();

        if (!checkedTasks.isEmpty()) {
            tasks.removeAll(checkedTasks);
            saveTasks();
            updateCounter();
        } else {
            showAlert("Please check the task you want to remove.");
        }
    }

    private void updateCounter() {
        long completedCount = tasks.stream().filter(Task::isCompleted).count();
        int totalCount = tasks.size();
        counterLabel.setText(String.format("%d tasks total, %d completed", totalCount, completedCount));
    }

    private void saveTasks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(taskFilePath))) {
            List<Task> taskList = new ArrayList<>(tasks);
            oos.writeObject(taskList);
        } catch (Exception e) {
            showAlert("Error saving tasks: " + e.getMessage());
        }
        updateCounter();
    }

    @SuppressWarnings("unchecked")
    private void loadTasks() {
        File file = new File(taskFilePath);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                List<Task> taskList = (List<Task>) ois.readObject();
                tasks.setAll(taskList);
            } catch (IOException | ClassNotFoundException e) {
                showAlert("Error loading tasks: " + e.getMessage());
            }
        }
        updateCounter();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
