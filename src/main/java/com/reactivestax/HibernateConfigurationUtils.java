package com.reactivestax;

//import io.reactivestax.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class HibernateConfigurationUtils {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    // Initialize Hibernate Session Factory by provide  Configuration in hibernate.cfg.xml
    // by default it looks for the file name hibernate.cfg.xml in the classpath
    public static SessionFactory getSessionFactoryByCodeConfig() {
        if (sessionFactory == null) {
            try {
                registry = new StandardServiceRegistryBuilder().configure().build(); // Create registry
                MetadataSources sources = new MetadataSources(registry); // Create MetadataSources
                Metadata metadata = sources.getMetadataBuilder().build(); // Create Metadata
                sessionFactory = metadata.getSessionFactoryBuilder().build(); // Create SessionFactory
            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
            sessionFactory = null;
        }
    }

    // Initialize Hibernate Session Factory by provide  Configuration in Code
    public static SessionFactory getSessionFactoryByXmlConfig()  {
        Configuration configuration = new Configuration();
        // Hibernate settings equivalent to hibernate.cfg.xml's properties
        Properties settings = new Properties();
        settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        settings.put(Environment.URL, "jdbc:mysql://localhost:3306/expenses?useSSL=false&serverTimezone=EST5EDT");
        settings.put(Environment.USER, "root");
        settings.put(Environment.PASS, "bootcamp");
        settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        settings.put(Environment.HBM2DDL_AUTO, "create");
        configuration.setProperties(settings);

        //Registering all the entities class, if you use hibernatr.cfg.xml then we add in the file there
//        configuration.addAnnotatedClass(Student.class);
//        configuration.addAnnotatedClass(Bootcamp.class);
//        configuration.addAnnotatedClass(Instructor.class);
//        configuration.addAnnotatedClass(Technology.class);
//        configuration.addAnnotatedClass(TechnologyReference.class);
//        configuration.addAnnotatedClass(BootcampLocation.class);

        registry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(registry);
        return sessionFactory;
    }
}
