package cat.udl.etrapp.server.daos;

import cat.udl.etrapp.server.controllers.SearchController;
import cat.udl.etrapp.server.db.DBManager;
import cat.udl.etrapp.server.models.Event;
import cat.udl.etrapp.server.utils.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventsDAO {

    private static EventsDAO instance;

    private EventsDAO() {

    }

    public static synchronized EventsDAO getInstance() {
        if (instance == null) instance = new EventsDAO();
        return instance;
    }

    public List<Event> getEvents(Integer offset, Integer maxResults) {
        final String SQLQuery;
        boolean paginated = false;
        if (offset == null || maxResults == null) {
            SQLQuery = "SELECT * FROM events";
        } else {
            paginated = true;
            SQLQuery = "SELECT * FROM events LIMIT ? OFFSET ?";
        }

        final List<Event> events = new ArrayList<>();

        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery)) {

            if (paginated) {
                statement.setInt(1, maxResults);
                statement.setInt(2, offset);
            }

            try (ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                events.add(eventFromResultSet(resultSet));
            }
            } catch (SQLException e) {
                System.err.println("Error in SQL: getEvents");
                System.err.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error in SQL: getEvents()");
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
                event = eventFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            System.err.println("Error in SQL: getAllEvents()");
            System.err.println(e.getMessage());
        }

        return event;
    }

    private Event eventFromResultSet(ResultSet resultSet) throws SQLException {
        final Event event = new Event();
        event.setId(resultSet.getLong("id"));
        event.setTitle(resultSet.getString("title"));
        event.setOwner(resultSet.getLong("owner_id"));
        event.setCategory(resultSet.getLong("category_id"));
        event.setDescription(resultSet.getString("description"));
        event.setStartsAt(resultSet.getTimestamp("starts_at").getTime());
        return event;
    }

    private PreparedStatement getEventByIdStatement(Connection connection, long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM events WHERE id = ?");
        statement.setLong(1, id);
        return statement;
    }

    public Event createEvent(Event event) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO events (title, description, owner_id, category_id, location, starts_at) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setString(1, event.getTitle());
            statement.setString(2, event.getDescription());
            statement.setLong(3, event.getOwner());
            statement.setLong(4, event.getCategory());
            statement.setString(5, event.getLocation());
            statement.setTimestamp(6, new Timestamp(event.getStartsAt()));
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    event.setId(rs.getLong(1));
                    SearchController.getInstance().addEvent(event);
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

    public boolean deleteEvent(long id) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM events WHERE id = ?");
        ) {
            statement.setLong(1, id);
            if (statement.executeUpdate() == 1) return true;
        } catch (SQLException e) {
            System.err.println("Error in SQL: deleteEvent()");
            System.err.println(e.getMessage());
        }
        return false;
    }

    public boolean editEvent(long id, Map<String, Object> changes) {
        boolean ok = false;

        if (!Event.updatable.containsAll(changes.keySet())) {
            return false;
        }

        try(Connection connection = DBManager.getConnection();
            CallableStatement statement = connection.prepareCall(Utils.generateSQLPatch("events", changes, id, Event.class))) {
            if (statement.executeUpdate() > 0) {
                ok = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ok;
    }


}
