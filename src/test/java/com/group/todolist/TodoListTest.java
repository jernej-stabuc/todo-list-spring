package com.group.todolist;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TodoListTest {
    private ToDoList toDoList;
    private DatabaseManager mockDatabaseManager;

    @Before
    public void setUp() {
        // Set up the test environment, initialize ToDoList with a mock DatabaseManager
        mockDatabaseManager = mock(DatabaseManager.class);
        toDoList = new ToDoList(mockDatabaseManager);
    }

    private Task createMockTask(long id, String description) {
        Task mockTask = new Task();
        mockTask.setId(id);
        mockTask.setDescription(description);
        return mockTask;
    }

    @Test
    public void testAddMultipleTasks() {
        // Create mock tasks
        Task task = createMockTask(1L, "Test case 1");
        Task task2 = createMockTask(2L, "Test case 2");

        // Add tasks to the ToDoList
        toDoList.addTask(task);
        toDoList.addTask(task2);

        // Verify that addTask() method of the mockDatabaseManager was called twice, once for each task
        verify(mockDatabaseManager, times(1)).addTask(task);
        verify(mockDatabaseManager, times(1)).addTask(task2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullTask() {
        // Pass null task to the ToDoList
        Task task = null;
        toDoList.addTask(task);

        // Verify that addTask() method of the mockDatabaseManager was never called (since task is null)
        verify(mockDatabaseManager, never()).addTask(task);
    }

    @Test
    public void testGetTaskById() {
        // Create a mock task
        Task task = createMockTask(1L, "Test task 1");

        // Define the behavior of getAllTasks() in the mockDatabaseManager to return the mock task
        when(mockDatabaseManager.getAllTasks()).thenReturn((List.of(task)));

        // Call the method being tested
        Task resultTask = toDoList.getTaskById(1L);

        // Verify that getAllTasks() method of the mockDatabaseManager was called
        verify(mockDatabaseManager).getAllTasks();

        // Verify that the returned task matches the mock task
        assertEquals(task, resultTask);
    }

    @Test
    public void testGetTaskByIdNotFound() {
        // Define the behavior of getAllTasks() in the mockDatabaseManager to return an empty list
        when(mockDatabaseManager.getAllTasks()).thenReturn(List.of());

        // Call the method being tested
        Task resultTask = toDoList.getTaskById(1L);

        // Verify that getAllTasks() method of the mockDatabaseManager was called
        verify(mockDatabaseManager).getAllTasks();

        // Verify that null is returned since the task with ID 1 is not found in the empty list
        assertNull(resultTask);
    }

    @Test
    public void testGetTaskByIdMultipleTasks() {
        // Create mock tasks
        Task task1 = createMockTask(1L, "Test task 1");
        Task task2 = createMockTask(2L, "Test task 2");

        // Define the behavior of getAllTasks() in the mockDatabaseManager to return the mock tasks
        when(mockDatabaseManager.getAllTasks()).thenReturn(List.of(task1, task2));

        // Call the method being tested
        Task resultTask1 = toDoList.getTaskById(1L);
        Task resultTask2 = toDoList.getTaskById(2L);

        // Verify that getAllTasks() method of the mockDatabaseManager was called twice, once for each task retrieval
        verify(mockDatabaseManager, times(2)).getAllTasks();

        // Verify that the returned tasks match the mock tasks
        assertEquals(task1, resultTask1);
        assertEquals(task2, resultTask2);
    }

    @Test
    public void testGetCompletedTasks() {
        // Create mock tasks
        Task task1 = createMockTask(1L, "Test task 1");
        Task task2 = createMockTask(2L, "Test task 2");

        // Mark task1 as completed
        task1.markAsCompleted();

        // Define the behavior of getAllTasks() in the mockDatabaseManager to return the mock tasks
        when(mockDatabaseManager.getAllTasks()).thenReturn(List.of(task1, task2));

        // Call the method being tested
        List<Task> completedTasks = toDoList.getCompletedTasks();

        // Verify that getAllTasks() method of the mockDatabaseManager was called
        verify(mockDatabaseManager).getAllTasks();

        // Verify that the list contains only the completed task (task1)
        assertEquals(1, completedTasks.size());
        assertEquals(task1, completedTasks.get(0));
    }

    @Test
    public void testGetCompletedTasksNoCompletedTasks() {
        // Create mock tasks (both not completed)
        Task task1 = createMockTask(1L, "Test task 1");
        Task task2 = createMockTask(2L, "Test task 2");

        // Define the behavior of getAllTasks() in the mockDatabaseManager to return the mock tasks
        when(mockDatabaseManager.getAllTasks()).thenReturn(List.of(task1, task2));

        // Call the method being tested
        List<Task> completedTasks = toDoList.getCompletedTasks();

        // Verify that getAllTasks() method of the mockDatabaseManager was called
        verify(mockDatabaseManager).getAllTasks();

        // Verify that the returned list is empty since there are no completed tasks
        assertTrue(completedTasks.isEmpty());
    }

    @Test
    public void testGetCompletedTasksNullTasks() {
        when(mockDatabaseManager.getAllTasks()).thenReturn(null);

        List<Task> completedTasks = toDoList.getCompletedTasks();

        verify(mockDatabaseManager).getAllTasks();

        assertTrue(completedTasks.isEmpty());
    }

    @Test
    public void testGetCompletedTasksEmptyTasks() {
        when(mockDatabaseManager.getAllTasks()).thenReturn(List.of());

        List<Task> completedTasks = toDoList.getCompletedTasks();

        verify(mockDatabaseManager).getAllTasks();

        assertTrue(completedTasks.isEmpty());
    }

    @Test
    public void testEditTaskDescription() {
        Task task = createMockTask(1L, "Test task 1");

        when(mockDatabaseManager.getAllTasks()).thenReturn(List.of(task));

        //Call the method being tested to edit the task description
        String newDescription = "Updated task description";
        toDoList.editTaskDescription(1L, newDescription);

        verify(mockDatabaseManager).getAllTasks();

        assertEquals(newDescription, task.getDescription());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testEditTaskDescriptionNegativeTaskId() {
        String newDescription = "Updated task description";
        toDoList.editTaskDescription(-1L, newDescription);
    }
}
