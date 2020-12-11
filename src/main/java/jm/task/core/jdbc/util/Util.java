package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Util {

    private static final String URL =  "jdbc:mysql://localhost:3306/Users?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String USERNAME =  "root";
    private static final String PASSWORD =  "123qwerty";

    public Connection getConnection() {
        Connection connection = null;

        try {
            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Подключение к базе данных  прошло успешно");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e){
            e.printStackTrace();

        }

        return connection;
    }


    // hibernate
    private  SessionFactory sessionFactory;
    public  SessionFactory getSessionFactory() {
            if (sessionFactory == null) {
        try {
            Configuration configuration = new Configuration();

            // Hibernate settings equivalent to hibernate.cfg.xml's properties
            Properties settings = new Properties();
            settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            settings.put(Environment.URL, "jdbc:mysql://localhost:3306/Users?serverTimezone=Europe/Moscow&useSSL=false");
            settings.put(Environment.USER, "root");
            settings.put(Environment.PASS, "123qwerty");
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
            settings.put(Environment.SHOW_SQL, "false");

            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");


            configuration.setProperties(settings);

            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        return sessionFactory;
    }


}
