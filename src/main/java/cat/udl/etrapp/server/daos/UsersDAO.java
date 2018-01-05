package cat.udl.etrapp.server.daos;

import cat.udl.etrapp.server.controllers.FirebaseController;
import cat.udl.etrapp.server.controllers.SearchController;
import cat.udl.etrapp.server.db.DBManager;
import cat.udl.etrapp.server.models.*;
import cat.udl.etrapp.server.utils.BCrypt;
import cat.udl.etrapp.server.utils.Password;
import cat.udl.etrapp.server.utils.Utils;
import com.sun.istack.internal.Nullable;

import java.sql.*;
import java.util.Map;

import static cat.udl.etrapp.server.utils.Utils.getHashedString;

public class UsersDAO {

    private static UsersDAO instance;

    private UsersDAO() {

    }

    public static synchronized UsersDAO getInstance() {
        if (instance == null) instance = new UsersDAO();
        return instance;
    }

    public User createUser(UserAuth userAuth) {
        // TODO: handle errors when registering duplicated username/email.
        User user = null;

        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO users (username, password_hashed, token, email, first_name, last_name, avatar_url) VALUES (?, ?, ?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS);
        ) {
            SessionToken tokenData = Utils.generateSessionToken();

            final String username = userAuth.getUsername();
            final String email = userAuth.getEmail();
            final String firstName = userAuth.getFirstName();
            final String lastName = userAuth.getLastName();
            final String avatarUrl = userAuth.getAvatarUrl();
            statement.setString(1, username);
            statement.setString(2, Password.hashPassword(userAuth.getPassword()));
            statement.setString(3, tokenData.getHashedToken());
            statement.setString(4, email);
            statement.setString(5, firstName);
            statement.setString(6, lastName);
            statement.setString(7, avatarUrl);
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getLong(1));
                    user.setToken(tokenData.getPlainToken());
                    user.setUsername(username);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setEmail(email);
                    user.setAvatarUrl(avatarUrl);

                    SearchController.getInstance().addUser(user);
                }
            } catch (SQLException e) {
                System.err.println("Error in SQL: createUser()");
                System.err.println(e.getMessage());
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error in SQL: createUser()");
            System.err.println(e.getMessage());
            return null;
        }
        return user;
    }

    @Nullable
    public User getUserById(long id) {
        User user = null;
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
        ) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    user = getUserFromResultSet(resultSet);
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

    public UserInfo getUserInfoById(long id) {
        return UserInfo.fromUser(getUserById(id));
    }

    @Nullable
    public User getUserByToken(String tokenPlainText) {
        User user = null;

        final String token = getHashedString(tokenPlainText.getBytes());

        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE token = ?");
        ) {
            statement.setString(1, token);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    user = getUserFromResultSet(resultSet);
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

    private User getUserFromResultSet(ResultSet resultSet, String token) throws SQLException {
        final User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setToken(token != null ? token : resultSet.getString("token"));
        user.setUsername(resultSet.getString("username"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setEmail(resultSet.getString("email"));
        user.setAvatarUrl(resultSet.getString("avatar_url"));
        return user;
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        return getUserFromResultSet(resultSet, null);
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

    public boolean validateToken(String tokenPlainText) {

        final String token = getHashedString(tokenPlainText.getBytes());

        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id FROM users WHERE token = ?");
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
        User user = null;

        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
        ) {
            statement.setString(1, credentials.getUsername());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    final String hashed_password = resultSet.getString("password_hashed");

                    if (Password.checkPassword(credentials.getPassword(), hashed_password)) {
                        SessionToken tokenData = Utils.generateSessionToken();
                        user = getUserFromResultSet(resultSet, tokenData.getPlainToken());
                        updateToken(tokenData.getHashedToken(), resultSet.getLong("id"));
                    }
                } else {
                    try (PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM users WHERE email = ?")) {
                        statement2.setString(1, credentials.getUsername());
                        try (ResultSet resultSet2 = statement2.executeQuery()) {
                            if (resultSet2.next()) {
                                final String hashed_password = resultSet2.getString("password_hashed");

                                if (Password.checkPassword(credentials.getPassword(), hashed_password)) {
                                    SessionToken tokenData = Utils.generateSessionToken();
                                    user = getUserFromResultSet(resultSet2, tokenData.getPlainToken());
                                    updateToken(tokenData.getHashedToken(), resultSet2.getLong("id"));
                                }
                            }
                        } catch (SQLException e) {
                            System.err.println("Error in SQL: authenticate()");
                        }
                    } catch (SQLException e) {
                        System.err.println("Error in SQL: authenticate()");
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error in SQL: authenticate()");
            }
        } catch (SQLException e) {
            System.err.println("Error in SQL: authenticate()");
        }
        return user;

    }

    public boolean deauthenticate(String token) {
        boolean deauth = false;
        User user = getUserByToken(token);
        if (user != null && user.getToken().equals(getHashedString(token.getBytes()))) {
            updateToken(null, user.getId());
            deauth = true;
        }
        return deauth;
    }

    public boolean editUser(long id, Map<String, Object> updates) {
        boolean ok = false;

        if (!User.updatable.containsAll(updates.keySet())) {
            return false;
        }

        // Hash the new password and remove plain text
        if (updates.containsKey("password")) {
            String password = (String)updates.get("password");
            updates.remove("password");
            updates.put("password_hashed", Password.hashPassword(password));
        }

        try(Connection connection = DBManager.getConnection();
            CallableStatement statement = connection.prepareCall(Utils.generateSQLPatch("users", updates, id, BaseUser.class))) {
            if (statement.executeUpdate() > 0) {
                ok = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ok;
    }

    public void updateNotificationToken(long id, String token) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO user_notification_tokens (user_id, token) VALUES (?, ?) ON CONFLICT (user_id) DO UPDATE SET token=?");
        ) {
            statement.setLong(1, id);
            statement.setString(2, token);
            statement.setString(3, token);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error in SQL: updateNotificationToken()");
            System.err.println(e.getMessage());
        }
    }

    public String getNotificationTokenById(long id) {
        String token = null;
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_notification_tokens WHERE user_id = ?");
        ) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    token = resultSet.getString("token");
                }
            } catch (SQLException e) {
                System.err.println("Error in SQL: getNotificationTokenById()");
                System.err.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error in SQL: getNotificationTokenById()");
            System.err.println(e.getMessage());
        }
        return token;
    }
}
