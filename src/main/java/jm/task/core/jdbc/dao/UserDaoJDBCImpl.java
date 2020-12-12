package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = getConnection()) {
            String SQL = "CREATE TABLE IF NOT EXISTS `Users` ( `id` bigint NOT NULL AUTO_INCREMENT, `name` varchar(255) DEFAULT NULL, `lastName` varchar(255) DEFAULT NULL, `age` int DEFAULT NULL, PRIMARY KEY (`id`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = getConnection()) {
            String SQL = "DROP TABLE IF EXISTS `Users`.`Users`";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    //todo ПРОВЕРИТЬ РАБОТУ saveUser
    public void saveUser(String name, String lastName, byte age) {


        try (Connection connection = getConnection()) {
            String SQL = "INSERT INTO `Users`.`Users`(`name`, `lastName`, `age`) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
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

        try (Connection connection = getConnection()) {
            String sql = "DELETE FROM `Users`.`Users` WHERE `id`=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> allUser = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String SQL = "SELECT id, name, lastName, age FROM Users";
            Statement statement = connection.createStatement();

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
        try (Connection connection = getConnection()) {
            String SQL = "DELETE FROM `Users`.`Users`";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.execute();
            System.out.println("Таблица полностью очищена");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
