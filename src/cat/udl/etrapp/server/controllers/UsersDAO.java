package cat.udl.etrapp.server.controllers;

import cat.udl.etrapp.server.db.DBManager;
import cat.udl.etrapp.server.models.Credentials;
import cat.udl.etrapp.server.models.User;
import cat.udl.etrapp.server.models.UserAuth;
import cat.udl.etrapp.server.utils.Password;
import cat.udl.etrapp.server.utils.Utils;
import com.sun.istack.internal.Nullable;

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

    public int createUser(UserAuth user) {
        // SQL: INSERT INTO users (username, password_hashed, token) VALUES (?, ?, ?)
        user.getUsername();
        Utils.generateSessionToken();
        Password.hashPassword(user.getPassword());
        return 1;
    }

    @Nullable
    public User getUserById(long id) {
        User user = null;
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id, token FROM users WHERE id = ?");
        ) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setToken(resultSet.getString("token"));
                }
            } catch (SQLException e) {
                System.err.println("Error in SQL: getUserById()");
                System.err.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error in SQL: getUserById()");
            System.err.println(e.getMessage());
        }
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

    public boolean deauthenticate(long id, String token) {
        boolean deauth = false;
        // TODO: Get user and delete token.
        User user = getUserById(id);
        if (user != null && user.getToken().equals(token)) {



            deauth = true;
        }
        return deauth;
    }
}
