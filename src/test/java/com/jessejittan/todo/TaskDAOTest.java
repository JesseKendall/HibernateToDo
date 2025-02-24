package com.jessejittan.todo;

import com.jessejittan.todo.dao.TaskDAO;
import com.jessejittan.todo.model.Task;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

public class TaskDAOTest {

    private TaskDAO taskDAO;

    @BeforeEach
    void setUp() {
        taskDAO = new TaskDAO();
    }

    @Test
    void testGetAllTasks() {
        // Create a new task
        Task task = new Task();
        task.setTitle("Test Task");

        // Save the task
        taskDAO.saveTask(task);

        // Retrieve all tasks
        List<Task> tasks = taskDAO.getAllTasks();

        // Ensure the list is not empty
        assertFalse(tasks.isEmpty(), "Task list should not be empty");

        // Check if the newly added task is present
        boolean taskExists = tasks.stream()
                .anyMatch(t -> "Test Task".equals(t.getTitle()));

        assertTrue(taskExists, "Saved task should exist in the retrieved task list");
    }
}