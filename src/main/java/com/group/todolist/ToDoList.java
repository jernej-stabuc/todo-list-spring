package com.group.todolist;

import java.util.List;

public class ToDoList {
    private DatabaseManager databaseManager;
    public ToDoList(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public void addTask(Task task) {
        if (task != null) {
            databaseManager.addTask(task);
        }
        else {
            throw new IllegalArgumentException("Task cannot be null");
        }
    }
    public List<Task> getAllTasks() {
        return databaseManager.getAllTasks();
    }
    public void setTasks(List<Task> tasks) {
        databaseManager.setTasks(tasks);
    }
    public Task getTaskById(long taskId) {
        if (taskId < 0) {
            throw new IllegalArgumentException("Task ID cannot be negative");
        }
        List<Task> allTasks = databaseManager.getAllTasks();
        for (Task task : allTasks) {
            if (task.getId() == taskId) {
                return task;
            }
        }
        return null;
    }
    // Implement methods to interact with the To-Do List
    // For example: addTask(Task task), getTaskById(long id), getAllTasks(), etc.
}
