package cat.udl.etrapp.server.daos;

import cat.udl.etrapp.server.controllers.FirebaseController;
import cat.udl.etrapp.server.db.DBManager;
import cat.udl.etrapp.server.models.EventMessage;
import cat.udl.etrapp.server.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventMessagesDAO {

    private static EventMessagesDAO instance;

    private EventMessagesDAO() {

    }

    public static synchronized EventMessagesDAO getInstance() {
        if (instance == null) instance = new EventMessagesDAO();
        return instance;
    }

    public EventMessage writeMessage(final EventMessage message, String authToken, final long eventId) {
        EventMessage eventMessage = null;

        final User user = UsersDAO.getInstance().getUserByToken(authToken);

        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO event_messages (message, user_id, event_id) VALUES (?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setString(1, message.getMessage());
            statement.setLong(2, user.getId());
            statement.setLong(3, eventId);
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    eventMessage = new EventMessage();
                    user.setId(rs.getLong(1));
                    eventMessage.setEventId(eventId);
                    eventMessage.setUserId(user.getId());
                    eventMessage.setMessage(message.getMessage());
                    // TODO: Write message to Firebase
                    FirebaseController.getInstance().writeMessage(eventId, eventMessage);
                }
            } catch (SQLException e) {
                System.err.println("Error in SQL: writeMessage()");
                return null;
            }

        } catch (SQLException e) {
            System.err.println("Error in SQL: writeMessage()");
            return null;
        }

        return eventMessage;
    }

    public List<EventMessage> getMessages(Integer offset, Integer maxResults, final long eventId) {

        final String SQLQuery;
        boolean paginated = false;
        if (offset == null || maxResults == null) {
            SQLQuery = "SELECT * FROM event_messages WHERE event_id = ?";
        } else {
            paginated = true;
            SQLQuery = "SELECT * FROM event_messages WHERE event_id = ? LIMIT ? OFFSET ?";
        }

        final List<EventMessage> eventMessages = new ArrayList<>();

        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery)) {

            statement.setLong(1, eventId);

            if (paginated) {
                statement.setInt(2, maxResults);
                statement.setInt(3, offset);
            }

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    EventMessage eventMessage = new EventMessage();
                    eventMessage.setMessage(resultSet.getString("message"));
                    eventMessage.setUserId(resultSet.getLong("user_id"));
                    eventMessages.add(eventMessage);
                }
            } catch (SQLException e) {
                System.err.println("Error in SQL: getEventMessages()");
            }
        } catch (SQLException e) {
            System.err.println("Error in SQL: getEventMessages()");
        }

        return eventMessages;
    }


}
