package com.jessejittan.todo;

import com.jessejittan.todo.dao.TaskDAO;  // Import DAO that handles Hibernate operations
import com.jessejittan.todo.model.Task;  // Import Task entity for database storage

import java.util.List;
import java.util.Scanner;

public class ToDoApp {
    public static void main(String[] args) {
        /** Replaced in-memory List with db interaction
         * Instead of storing tasks in an in-memory List (previously handled by ToDoList),
         * now use TaskDAO, which interacts with MySQL via Hibernate.
         */
        TaskDAO taskDAO = new TaskDAO();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nTo-Do List Application");
            System.out.println("1. Add Task");
            System.out.println("2. Remove Task");
            System.out.println("3. Display Tasks");
            System.out.println("4. Mark Task as Complete");
            System.out.println("5. Exit");
            System.out.print("Please make a selection: ");

            int selection = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (selection) {
                case 1:
                    System.out.println("Enter task description: ");
                    String description = scanner.nextLine();

                    // Replaced old addItem() method with db saveTask()
                    Task newTask = new Task(description, false); // Default status false - or not completed
                    taskDAO.saveTask(newTask);
                    System.out.println("Task added successfully.");
                    break;

                case 2:
                    System.out.println("Enter the task ID to remove: ");
                    int taskId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    // Replaced old removeItem() method with deleteTaskById
                    taskDAO.deleteTaskById(taskId);
                    System.out.println("Task removed successfully.");
                    break;

                case 3:
                    // Replaced old displayItems() method with getAllTasks() db query
                    List<Task> tasks = taskDAO.getAllTasks();
                    if (tasks.isEmpty()) {
                        System.out.println("Your to-do list is empty.");
                    } else {
                        System.out.println("\nYour To-Do List:");
                        for (Task task : tasks) {
                            System.out.println(task.getId() + ". " + task.getTitle() + " - [" + task.isCompleted() + "]");
                        }
                    }
                    break;

                case 4:
                    System.out.println("Enter the task ID to mark as complete: ");
                    int completeId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    // Replaced old "IN-MEMORY STATUS CHANGE"
                    taskDAO.markTaskAsComplete(completeId);
                    System.out.println("Task marked as complete.");
                    break;

                case 5:
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid selection. Please try again.");
                    break;
            }
        }

        scanner.close();
    }
}