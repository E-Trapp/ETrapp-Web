package cat.udl.etrapp.server.controllers;

import cat.udl.etrapp.server.db.DBManager;
import cat.udl.etrapp.server.models.Credentials;
import cat.udl.etrapp.server.models.User;
import cat.udl.etrapp.server.utils.Password;
import cat.udl.etrapp.server.utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersDAO {

    private static UsersDAO instance;

    private UsersDAO() {

    }

    public static synchronized UsersDAO getInstance() {
        if (instance == null) instance = new UsersDAO();
        return instance;
    }

    public User createUser(User user, String password) {
        // SQL: INSERT INTO users (username, password_hashed, token) VALUES (?, ?, ?)
        user.getUsername();
        Utils.generateSessionToken();
        Password.hashPassword("pwpw");
        return user;
    }

    public void updateToken(String token, long id) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE users SET token = ? WHERE id = ?");
        ) {
            statement.setString(1, token);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error in SQL: updateToken()");
            System.err.println(e.getMessage());
        }
    }

    public boolean validateToken(String token) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE token = ?");
        ) {
            statement.setString(1, token);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            } catch (SQLException e) {
                System.err.println("Error in SQL: validateToken()");
                System.err.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error in SQL: validateToken()");
            System.err.println(e.getMessage());
        }
        return false;
    }

    public User authenticate(Credentials credentials) {
        User user = new User();
        // "SELECT id, username, password_hashed FROM users WHERE username = ?"
        // Password.checkPassword(credentials.getPassword(), resultSet.get..(password_hashed)..
        // updateToken();
        return user;
    }
}
