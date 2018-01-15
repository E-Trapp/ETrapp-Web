package cat.udl.etrapp.server.daos;

import cat.udl.etrapp.server.db.DBManager;
import cat.udl.etrapp.server.models.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Long.getLong;

public class ScoresDAO {
    private static ScoresDAO instance;

    private ScoresDAO() {
    }

    public static synchronized ScoresDAO getInstance() {
        if (instance == null) instance = new ScoresDAO();
        return instance;
    }

    public Boolean getSubscribed(long eventId, long userId) {
        final String SQLQuery;

        SQLQuery = "SELECT id FROM scores WHERE event_id = ? AND user_id = ?";

        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery)) {
            statement.setLong(1, eventId);
            statement.setLong(2, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();

            } catch (SQLException e) {
                System.err.println("Error in SQL: getEvents");
                System.err.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error in SQL: getEvents()");
            System.err.println(e.getMessage());
        }
        return false;
    }


    public boolean like(long eventId, long userId) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO scores (event_id, user_id, score) VALUES (?, ?, 1)");
        ) {
            statement.setLong(1, eventId);
            statement.setLong(2, userId);
            if (statement.executeUpdate() == 1) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error in SQL: deleteEvent()");
            System.err.println(e.getMessage());
        }
        return false;
    }

    public boolean dislike(long eventId, long userId) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO scores (event_id, user_id, score) VALUES (?, ?, 0)");
        ) {
            statement.setLong(1, eventId);
            statement.setLong(2, userId);
            if (statement.executeUpdate() == 1) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error in SQL: deleteEvent()");
            System.err.println(e.getMessage());
        }
        return false;
    }

    public Map<String, Long> getScoresFromEvent(Long id) {
        Map<String, Long> data = null;


        final String SQLQuery;

        SQLQuery = "SELECT score FROM scores WHERE event_id = ?";

        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                data = new HashMap<>();
                long likes = 0;
                long dislikes = 0;
                long score;

                while(resultSet.next()) {
                    long a = resultSet.getLong("score");
                    if (a == 1) likes++;
                    else dislikes++;
                }

                score = (int)(100 * (likes/(double)(likes+dislikes)));

                data.put("likes", likes);
                data.put("dislikes", dislikes);
                data.put("score", score);

            } catch (SQLException e) {
                System.err.println("Error in SQL: getEvents");
                System.err.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error in SQL: getEvents()");
            System.err.println(e.getMessage());
        }

        return data;
    }
}
