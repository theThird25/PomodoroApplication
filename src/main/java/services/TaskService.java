package services;

import entity.Task;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskService {

    private List<Task> tasks = new ArrayList<>();
    private static final String FILE_NAME = "tasks.txt";

    public TaskService() {
        loadTasks();
    }

    private void addTask(Task task) {
        tasks.add(task);
        saveTasks();
    }

    public void removeTasks(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            saveTasks();
        }
    }

    public void markTaskComplete(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markComplete();
            saveTasks();
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }

    private void saveTasks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadTasks() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                tasks = (List<Task>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
