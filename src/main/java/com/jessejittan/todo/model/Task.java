package com.jessejittan.todo.model;

// JPA (Jakarta Persistence API) defines how Java objects should be mapped to the DB, a set of rules
import jakarta.persistence.*;

/**
 * The Task class is a Hibernate entity that defines how a task should be stored in the db
 * Maps to the db table named tasks
 * Hibernate creates, updates, and deletes records based on this Task class
 * No db logic is in here, just the structure (fields, getters, setters)
 * Defines the structure of the tasks table, and without it, there's nothing for hibernate to store
 */
@Entity     // Marks the class as a Hibernate entity (meaning it will be mapped to a DB table)
@Table(name = "tasks")    // Specifies the DB table name, if missing, the table name will default to the class name
public class Task {

    @Id     // Marks the field var. as the primary key of the table
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //Defines the PK generation strategy. IDENTITY auto-generates the ID
    private int id;

    @Column(name = "title")     // Specifies that this field maps to the title column in the tasks table
    private String title;

    @Column(name = "completed")     // Maps the field to the completed column in the task table
    private boolean completed;

    /**
     * Default constructor required by Hibernate
     * It requires a no-argument constructor to create instances dynamically, without a no-argument constructor,
     * Hibernate doesn’t know how to create an instance before setting its values. For example: retrieving a task with
     * no arguments, so it cannot create an instance to set its values.
     */
    public Task() {}

    // Constructor to initialize a Task with arguments
    public Task(String title, boolean completed) {
        this.title = title;
        this.completed = completed;
    }

    // Getters and setter methods to allow Hibernate to access and modify the private fields.
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Method to return string representation of the Task obj
     * @return a string describing the task in a readable format
     */
    @Override
    public String toString() {
        return "Task [id=" + id + ", title=" + title + ", completed=" + completed + "]";
    }
}
