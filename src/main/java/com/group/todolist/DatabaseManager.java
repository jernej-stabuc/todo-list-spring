package com.group.todolist;

import java.util.List;

public interface DatabaseManager {
    void addTask(Task task);
    List<Task> getAllTasks();
    void setTasks (List<Task> customTasks);
}
