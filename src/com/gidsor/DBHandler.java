package com.gidsor;

import org.sqlite.JDBC;

import java.sql.*;
import java.util.*;

public class DBHandler {
    private static final String DB_URL = "jdbc:sqlite:/Users/gidsor/Desktop/UserAuthenticationHomework/users.sqlite";

    public static DBHandler instance = null;

    public static synchronized DBHandler getInstance() throws SQLException {
        if (instance == null)
            instance = new DBHandler();
        return instance;
    }

    private Connection connection;

    public DBHandler() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(DB_URL);
    }

    public void addUser(String username, String password, String fullname, String email) {
        String query = String.format("INSERT INTO users(username, password, fullname, email) VALUES ('%s', '%s', '%s', '%s')", username, password, fullname, email);
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUser(String username) {
        String query = String.format("DELETE FROM users WHERE username='%s'", username);
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeUser(String username, String password, String fullname, String email) {
        String query = String.format("UPDATE users SET password='%s', fullname='%s', email='%s' WHERE username='%s'", password, fullname, email, username);
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUser(String username) {
        try (Statement statement = this.connection.createStatement()) {
            User user = null;
            String query = String.format("SELECT * FROM users WHERE username='%s'", username);
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                user = new User(resultSet.getInt("id"),
                                resultSet.getString("username"),
                                resultSet.getString("password"),
                                resultSet.getString("fullname"),
                                resultSet.getString("email"));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<User> getAllUsers() {
        try (Statement statement = this.connection.createStatement()) {
            List<User> users = new ArrayList<User>();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                users.add(new User(resultSet.getInt("id"),
                                    resultSet.getString("username"),
                                    resultSet.getString("password"),
                                    resultSet.getString("fullname"),
                                    resultSet.getString("email")));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
