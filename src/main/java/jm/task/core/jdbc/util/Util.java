package jm.task.core.jdbc.util;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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


}
