package com.group.todolist;
import java.util.ArrayList;
import java.util.List;

public class MockDatabaseManager implements DatabaseManager {
    private List<Task> tasks = new ArrayList<>();
    @Override
    public void addTask(Task task) {
        tasks.add(task);
    }
    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    // Additional method to set the list of tasks for testing scenarios
    public void setTasks(List<Task> customTasks) {
        tasks.clear();
        tasks.addAll(customTasks);
    }
}
