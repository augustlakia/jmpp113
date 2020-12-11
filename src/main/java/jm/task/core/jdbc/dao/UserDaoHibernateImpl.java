package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    Util util = null;

    public UserDaoHibernateImpl() {
        util = new Util();

    }

    @Override
    public void createUsersTable() {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query sql = session.createSQLQuery("create table IF NOT EXISTS User (id bigint not null auto_increment, age tinyint, lastName varchar(255), name varchar(255), primary key (id)) engine=InnoDB");
            sql.executeUpdate();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query sql = session.createSQLQuery("DROP TABLE IF EXISTS User");
            sql.executeUpdate();
            transaction.commit();

            session.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        try (Session session = util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            transaction.begin();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
                transaction.commit();
                System.out.printf("Пользователь с id $d удален", id);
            }
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = (List<User>) util.getSessionFactory().openSession().createQuery("from User").list();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = util.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            Query sql = session.createSQLQuery("DELETE FROM User");
            sql.executeUpdate();
            session.close();
        } catch (Exception e) {

        }
    }
}
