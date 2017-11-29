package cat.udl.etrapp.server.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cat.udl.etrapp.server.db.DBManager;
import cat.udl.etrapp.server.models.Event;

public class EventsDAO {
	
	private static EventsDAO instance;
		
	private EventsDAO() {
		
	}
	
	public static synchronized EventsDAO getInstance() {
		if (instance == null) instance = new EventsDAO();
		return instance;
	}
	
	public List<Event> getAllEvents() {
		List<Event> events = new ArrayList<>();

	    try (
	        Connection connection = DBManager.getConnection();
	        PreparedStatement statement = connection.prepareStatement("SELECT id, title FROM events");
	        ResultSet resultSet = statement.executeQuery();
	    ) {
	        while (resultSet.next()) {
	            Event event = new Event();
	            event.setId(resultSet.getLong("id"));
	            event.setTitle(resultSet.getString("title"));	            
	        }
	    } catch (SQLException e) {
	    	System.err.println("Error in SQL: getAllEvents()");
	    	System.err.println(e.getMessage());
	    }

		return events;
	}

	public Event getEventById(Long id) {
		Event event = null;

	    try (Connection connection = DBManager.getConnection()) {
	    	PreparedStatement statement = getEventByIdStatement(connection, id);
	        ResultSet resultSet = statement.executeQuery();
	        while (resultSet.next()) {
	        	event = new Event();
	            event.setId(resultSet.getLong("id"));
	            event.setTitle(resultSet.getString("title"));	            
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

}
