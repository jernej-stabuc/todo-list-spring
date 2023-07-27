package com.group.todolist;

import java.util.Comparator;

public class TaskComparatorFactory {
    public static Comparator<Task> getDescriptionComparator() {
        return Comparator.comparing(Task::getDescription);
    }
    public static Comparator<Task> getLabelComparator() {
        return Comparator.comparing(Task::getPriority);
    }
}
