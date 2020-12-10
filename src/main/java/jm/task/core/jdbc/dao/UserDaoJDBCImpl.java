package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    Connection connection = getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        PreparedStatement preparedStatement = null;
        String SQL = "CREATE TABLE `Users` ( `id` bigint NOT NULL AUTO_INCREMENT, `name` varchar(255) DEFAULT NULL, `lastName` varchar(255) DEFAULT NULL, `age` int DEFAULT NULL, PRIMARY KEY (`id`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
        try {
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "Users", null);
            tables.next();
            if (tables.next()) {
                System.out.println("Таблица Users уже существует, не пытайтесь создать еще одну");
            } else {
                preparedStatement = connection.prepareStatement(SQL);
                preparedStatement.execute();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public void dropUsersTable() {
        PreparedStatement preparedStatement = null;
        String SQL = "DROP TABLE `Users`.`Users`";
        try {
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "Users", null);
            tables.next();
            if (tables.next()) {
                preparedStatement = connection.prepareStatement(SQL);
                preparedStatement.execute();
                System.out.println("Таблица удалена из базы данных");
            } else {
                System.out.println("Таблицы для удаления не существует");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    //todo ПРОВЕРИТЬ РАБОТУ saveUser
    public void saveUser(String name, String lastName, byte age) {
        PreparedStatement preparedStatement = null;
        String SQL = "INSERT INTO `Users`.`Users`(`name`, `lastName`, `age`) VALUES (?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        PreparedStatement preparedStatement = null;

        String sql = "DELETE FROM `Users`.`Users` WHERE `id`=?";

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> allUser = new ArrayList<>();
        String SQL = "SELECT id, name, lastName, age FROM Users";

        Statement statement = null;

        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                allUser.add(user);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allUser;
    }

    public void cleanUsersTable() {
        PreparedStatement preparedStatement = null;
        String SQL = "DELETE FROM `Users`.`Users`";
        try {
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.execute();
            System.out.println("Таблица полностью очищена");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
