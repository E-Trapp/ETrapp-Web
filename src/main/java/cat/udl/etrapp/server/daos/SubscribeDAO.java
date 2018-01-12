package cat.udl.etrapp.server.daos;

import cat.udl.etrapp.server.models.Subscribe;
import cat.udl.etrapp.server.utils.Utils;
import cat.udl.etrapp.server.db.DBManager;

import java.sql.*;


public class SubscribeDAO {

    private static SubscribeDAO instance;

    private SubscribeDAO() {
    }

    public static synchronized SubscribeDAO getInstance() {
        if (instance == null) instance = new SubscribeDAO();
        return instance;
    }

    public Subscribe createSubscribe(Subscribe subscribe) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO subscribe_events (event_id, user_id)  VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setLong(1, subscribe.getEvent_id());
            statement.setLong(2, subscribe.getUser_id());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error in SQL: createEvent()");
            System.err.println(e.getMessage());
            return null;
        }
        return subscribe;
    }
}
