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
    public void testEmptyList() {
        // Test if the list is empty
        // Mock the DatabaseManager to return an empty list when getAllTasks() is called
        when(mockDatabaseManager.getAllTasks()).thenReturn(List.of());

        // Call the method being tested
        List<Task> tasks = toDoList.getAllTasks();

        // Verify that the getAllTasks() method of the mockDatabaseManager was called
        verify(mockDatabaseManager).getAllTasks();

        // Verify that the list is empty
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void testAddMultipleTasks() {
        Task task = createMockTask(1L, "Test case 1");
        Task task2 = createMockTask(2L, "Test case 2");

        toDoList.addTask(task);
        toDoList.addTask(task2);

        verify(mockDatabaseManager, times(1)).addTask(task);
        verify(mockDatabaseManager, times(1)).addTask(task2);
    }

    @Test
    public void testAddNullTask() {
        Task task = null;
        toDoList.addTask(task);
        verify(mockDatabaseManager, never()).addTask(task);
    }


    @Test
    public void testGetAllTasks() {
        // create a list of mock tasks
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(createMockTask(1L, "Task 1"));
        mockTasks.add(createMockTask(2L, "Task 2"));
        mockTasks.add(createMockTask(3L, "Task 3"));

        //define the behaviour of the getAllTasks() method in the mockDatabaseManager to return the mockTasks list
        when(mockDatabaseManager.getAllTasks()).thenReturn(mockTasks);

        //call method being tested
        List<Task> tasks = toDoList.getAllTasks();

        //verify that the getAllTasks method of the mockDatabaseManager was called
        Mockito.verify(mockDatabaseManager).getAllTasks();

        // Verify that the list of tasks returned matches the mockTasks list
        assertEquals(3, tasks.size());
        assertEquals(mockTasks, tasks);
    }

    @Test
    public void testGetTaskById() {
        Task task = createMockTask(1L, "Test task 1");

        //define the behaviour of getAllTasks() in the mockDatabaseManager to return the mock task
        when(mockDatabaseManager.getAllTasks()).thenReturn((List.of(task)));

        //call the method being tested
        Task resultTask = toDoList.getTaskById(1L);

        verify(mockDatabaseManager).getAllTasks();

        // verify that the returned task matches the mock task
        assertEquals(task, resultTask);
    }

    @Test
    public void testGetTaskByIdNotFound() {
        when(mockDatabaseManager.getAllTasks()).thenReturn(List.of());

        Task resultTask = toDoList.getTaskById(1L);

        verify(mockDatabaseManager).getAllTasks();

        assertNull(resultTask);
    }

    @Test
    public void testGetTaskByIdMultipleTasks() {
        Task task1 = createMockTask(1L, "Test task 1");
        Task task2 = createMockTask(2L, "Test task 2");

        when(mockDatabaseManager.getAllTasks()).thenReturn(List.of(task1, task2));

        //call the method being tested
        Task resultTask1 = toDoList.getTaskById(1L);
        Task resultTask2 = toDoList.getTaskById(2L);

        verify(mockDatabaseManager, times(2)).getAllTasks();

        //verify that the returned tasks match the mock tasks
        assertEquals(task1, resultTask1);
        assertEquals(task2, resultTask2);
    }
}
