package com.jessejittan.todo.dao;

import com.jessejittan.todo.model.Task;                 // Imports the Task entity, which the DAO interacts with
import com.jessejittan.todo.util.HibernateUtil;         // Imports the utility class (HibernateUtil) that manages Hibernate SessionFactory
import jakarta.persistence.Query;
import org.hibernate.Session;                           // Represents a single unit of work with the db - manages database operations (like opening a connection, querying, and updating) - CRUD.
import org.hibernate.Transaction;                       // Helps manage transactions ensuring data integrity in case of failures.
import org.hibernate.query.MutationQuery;

import java.util.List;                                  // Utility for handling lists

/**
 * TaskDAO stands for Task Data Access Object
 * Naming format <EntityName>DAO indicating that this class handles db operations for the Task entity
 * Responsible for interacting with the db using Hibernate with the blueprint of directions from the Task class.
 * Provides the logic - methods to save, retrieve, update, tasks using Hibernate.
 */
public class TaskDAO {

    // No field variable necessary because TaskDAO serves as a utility class for db operations, every method opens a new Hibernate session, performs an action, then closes it.

    // Method for saving new Task obj's to the db
    public void saveTask(Task task) {

        //A transaction is a sequence of one or more db operations that must be executed as a single unit
        // It is set to null b/c the transaction has not started yet
        Transaction transaction = null;

        // Opens a Hibernate session to connect to the db - try w. resources to ensure closing.
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {          // openSession() creates a new db connection, and session is the obj that allows you to interact w the db
            transaction = session.beginTransaction();       // Starts a new transaction. Remember: any operations between beginTransaction() and commit() are part of the same transaction
            session.persist(task);                          // ensure a new record is created and saved
            transaction.commit();                           // Changes made during the transaction are permanently saved in the db
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Method to retrieve all Task objects from the db
     * List<Task> specifies that the method will return a list of Task objects
     * No Transaction required because no changes are made to the db, same reason no rollback is needed
     * Hibernate will throw an exception if the query fails
     */
    public List<Task> getAllTasks() {

        // Opens a Hibernate session to connect to the db - try w. resources
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            // "FROM Task" is a Hibernate Query Language (HQL) - similar to SQL - .list() coverts the query results as a List<Task>
            return session.createQuery("FROM Task", Task.class).list();
        }
    }

    /**
     * Method to delete Task by its id and update the db
     * @param id
     */
    public void deleteTaskById(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // MutationQuery used for Update, Delete, or Insert queries
            // Whereas Query<T> Used for Select queries
            MutationQuery query = session.createMutationQuery("DELETE FROM Task WHERE id = :taskId");
            query.setParameter("taskId", id);
            int result = query.executeUpdate(); // Executes and returns the number of rows deleted

            if (result == 0) {
                System.out.println("Task not found.");
            } else {
                System.out.println("Task has been deleted.");
            }

            transaction.commit(); // Commit the transaction if successful

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback(); // Rollback if an error occurs
            }
            e.printStackTrace();
        }
    }

    /**
     * Marks a task as completed by updating its status in the database.
     * Uses HQL (Hibernate Query Language) to perform an efficient update operation.
     */
    public void markTaskAsComplete(int id) {
        Transaction transaction = null; // Declare transaction variable

        // getCurrentSession() auto manages transactions, prevents unnecessary session closures and avoids "session is closed" errors.
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction(); // Start a transaction since we are modifying data

            // Queries the db for a Task entity with this specific id- if found, it returns the Task obj; if not, it returns null.
            Task task = session.get(Task.class, id);

            if (task != null) {     // Check if task exist
                if (task.isCompleted()) {       // Check if it is already completed
                    System.out.println("Task is already completed.");
                } else {
                    task.setCompleted(true);    // Mark as completed - true
                    session.persist(task);        // Save changes in the db
                    transaction.commit();       // Commit the transaction
                    System.out.println("Task has been updated to completed.");
                }
            } else {
                    System.out.println("Task is not found.");
                }

            } catch (Exception e) {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();  // Rollback if error occurs
            }
                e.printStackTrace();
        }
    }
}
