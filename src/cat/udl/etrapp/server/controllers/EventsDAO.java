package cat.udl.etrapp.server.controllers;

import cat.udl.etrapp.server.db.DBManager;
import cat.udl.etrapp.server.models.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventsDAO {

    private static EventsDAO instance;

    private EventsDAO() {

    }

    public static synchronized EventsDAO getInstance() {
        if (instance == null) instance = new EventsDAO();
        return instance;
    }

    public List<Event> getAllEvents() {
        final List<Event> events = new ArrayList<>();

        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM events");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Event event = new Event();
                event.setId(resultSet.getLong("id"));
                event.setTitle(resultSet.getString("title"));
                event.setOwner(resultSet.getLong("owner_id"));
                events.add(event);
            }
        } catch (SQLException e) {
            System.err.println("Error in SQL: getAllEvents()");
            System.err.println(e.getMessage());
        }

        return events;
    }

    public List<Event> getEventsPaginated(int offset, int maxResults) {
        final List<Event> events = new ArrayList<>();

        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM events LIMIT ? OFFSET ?")) {
            statement.setInt(1, maxResults);
            statement.setInt(2, offset);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Event event = new Event();
                    event.setId(resultSet.getLong("id"));
                    event.setTitle(resultSet.getString("title"));
                    event.setOwner(resultSet.getLong("owner_id"));
                    // System.out.println("Event created at: " + resultSet.getTimestamp("created_at").getTime());
                    events.add(event);
                }
            } catch (SQLException e) {
                System.err.println("Error in SQL: getEventsPaginated()");
                System.err.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error in SQL: getEventsPaginated()");
            System.err.println(e.getMessage());
        }

        return events;
    }

    public Event getEventById(long id) {
        Event event = null;

        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = getEventByIdStatement(connection, id);
             ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                event = new Event();
                event.setId(resultSet.getLong("id"));
                event.setTitle(resultSet.getString("title"));
                event.setOwner(resultSet.getLong("owner_id"));
            }
        } catch (SQLException e) {
            System.err.println("Error in SQL: getAllEvents()");
            System.err.println(e.getMessage());
        }

        return event;
    }

    private PreparedStatement getEventByIdStatement(Connection connection, long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM events WHERE id = ?");
        statement.setLong(1, id);
        return statement;
    }

    public Event createEvent(Event event) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO events (title, description, owner_id, category_id) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setString(1, event.getTitle());
            statement.setString(2, event.getDescription());
            statement.setLong(3, event.getOwner());
            statement.setLong(4, event.getCategory());
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    event.setId(rs.getLong(1));
                }
            } catch (SQLException e) {
                System.err.println("Error in SQL: createEvent()");
                System.err.println(e.getMessage());
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error in SQL: createEvent()");
            System.err.println(e.getMessage());
            return null;
        }
        return event;
    }


}
