package view;

import entity.Task;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

public class TaskCell extends ListCell<Task> {

    private final CheckBox checkBox = new CheckBox();
    private Task currentTask;
    private final TaskCellCallback callback;
    private boolean isNewlyAdded = false;

    public interface TaskCellCallback {
        void onTaskCompletionChanged(Task task);
    }

    public TaskCell(TaskCellCallback callback) {
        this.callback = callback;

        checkBox.setOnAction(event -> {
            if (currentTask != null) {
                currentTask.setCompleted(checkBox.isSelected());
                if (callback != null) {
                    callback.onTaskCompletionChanged(currentTask);
                }
            }
        });
    }

    public void markAsNewlyAdded() {
        isNewlyAdded = true;
        updateItem(getItem(), isEmpty());
    }

    @Override
    protected void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);

        if (empty || task == null) {
            setText(null);
            setGraphic(null);
            // Still apply the background color, don't set style to null
            setStyle("-fx-background-color: #c7eae4;");
            currentTask = null;
        } else {
            currentTask = task;
            checkBox.setSelected(task.isCompleted());

            HBox hbox = new HBox(20);
            hbox.getChildren().add(checkBox);

            Label label = new Label(task.getDescription());
            label.setStyle("-fx-font-size: 14px;");

            // Add padding to entire cell content
            hbox.setPadding(new javafx.geometry.Insets(5, 10, 5, 10));

            hbox.getChildren().add(label);
            setGraphic(hbox);
        }
    }
}