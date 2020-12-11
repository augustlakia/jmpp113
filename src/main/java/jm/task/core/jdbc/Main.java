package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args)  {
        UserServiceImpl users = new UserServiceImpl();
        users.createUsersTable();
        users.saveUser("Alexey", "Navalny", (byte) 45);
        users.saveUser("Vladimir", "Putin", (byte) 70);
        users.saveUser("Friedrich", "Nietzsche", (byte) 41);
        users.saveUser("Artur", "Schopenhauer", (byte) 55);
        for (User i:users.getAllUsers()) {
            System.out.println(i.toString());
        }
        users.cleanUsersTable();
        users.dropUsersTable();
    }


}
