package com.jessejittan.todo.util;

//Note: Do not use java.lang.module.Configuration for Hibernate—it has nothing to do with databases.
// Do not use import com.mysql.cj.xdevapi.SessionFactory

import org.hibernate.SessionFactory;        // Responsible for creating Session objects, which manage database interactions
import org.hibernate.cfg.Configuration;     // Used to load Hibernate configurations (from hibernate.cfg.xml)

/**
 * The HibernateUtil class is a utility class that manages the Hibernate SessionFactory, which
 * is responsible for creating and managing DB connections.
 */
public class HibernateUtil {

    // Singleton instance of SessionFactory - only one instance of it will be created, saving resources
    private static final SessionFactory sessionFactory = buildSessionFactory();

    /**
     * Builds the SessionFactory fm the cfg.xml file
     * @return the configured SessionFactory
     */
    private static SessionFactory buildSessionFactory() {
        try {

            // Load configuration from hibernate.cfg.xml and build the SessionFactory
            // Remember new Configuration().configure() is called method chaining
            return new Configuration().configure("hibernate/hibernate.cfg.xml").buildSessionFactory();

            // Top most superclass - ensures everything is caught.
        } catch (Throwable ex) {
            System.err.println("SessionFactory creation failed: " + ex);

            // Stops the app from continuing if Hibernate fails to initialize
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Provides access to the SessionFactory instance so other parts of the app can call and returns that SessionFactory instance
     * @return if one exist - if it does not, it immediately calls the buildSessionFactory() to create one
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
