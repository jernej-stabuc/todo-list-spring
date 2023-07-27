package com.group.todolist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public List<Task> getCompletedTasks() {
        List<Task> allTasks = databaseManager.getAllTasks();
        List<Task> completedTasks = new ArrayList<>();
        if (allTasks != null) {
            for (Task task: allTasks) {
                if (task.isCompleted()) {
                    completedTasks.add(task);
                }
            }
        }
        return completedTasks;
    }
    public void editTaskDescription(long taskId, String newDescription) {
        List<Task> allTasks = databaseManager.getAllTasks();

        boolean foundTask = false;

        for (Task task: allTasks) {
            if (task.getId() == taskId) {
                task.setDescription(newDescription);
                foundTask = true;
                break;
            }
        }
        if (!foundTask) {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found.");
        }

    }
    public List<Task> sortTasksByCriteria(Comparator<Task> taskComparator) {
        List<Task> allTasks = databaseManager.getAllTasks();

        // Create a copy of the tasks list to avoid modifying the original list
        List<Task> sortedTasks = new ArrayList<>(allTasks);

        // Sort the tasks based on the provided comparator
        sortedTasks.sort(taskComparator);

        return sortedTasks;
    }


    // Implement methods to interact with the To-Do List
    // For example: addTask(Task task), getTaskById(long id), getAllTasks(), etc.
}
