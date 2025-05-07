package entity;

import java.io.Serializable;

public class Task implements Serializable {

    private static final long serialVersionUID = 1L;
    private  String text;
    private boolean completed;
    private String description;

    public Task(String text, boolean completed) {
        this.text = text;
        this.completed = completed;
        this.description = text;
    }

    public Task(String text, boolean completed, String description) {
        this.text = text;
        this.completed = completed;
        this.description = description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return text;
    }
}
